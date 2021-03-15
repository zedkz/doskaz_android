package com.zed.kz.doskaz.main.presentation.auth.sms

import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AuthCredential
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.AuthInteractor
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class SmsPresenter(
    private val phone: String,
    private var storeVerificationId: String,
    private var resentToken: PhoneAuthProvider.ForceResendingToken,
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor
) : BasePresenter<SmsFragmentView>(){

    fun onSendBtnClicked(sms: String){
        viewState?.showProgressBar(true)
        val credential = PhoneAuthProvider.getCredential(storeVerificationId, sms)

        FirebaseAuth.getInstance()
            .signInWithCredential(credential)
            .addOnCompleteListener { task ->  
                if (task.isSuccessful){
                    task.result
                        ?.user
                        ?.getIdToken(true)
                        ?.addOnCompleteListener {
                            it.result?.token?.let { idToken -> signIn(idToken) }
                        }
                }else{
                    viewState?.showMessage(resourceManager.getString(R.string.warning_wrong_code))
                    viewState?.showProgressBar(false)
                }
            }
    }

    private fun signIn(idToken: String){
        authInteractor.authFirebase(AuthCredential(idToken = idToken))
            .subscribe(
                {
                    if (it.code() == 201){
                        LocalStorage.setUser(it.body())
                        it.body()?.token?.let { token -> LocalStorage.setAccessToken(token) }
                        viewState?.openBonusFragment()
                    }else if (it.code() == 200){
                        LocalStorage.setUser(it.body())
                        it.body()?.token?.let { token -> LocalStorage.setAccessToken(token) }
                        viewState?.openMainFragment()
                    }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showMessage(resourceManager.getString(R.string.something_go_wrong))
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun sentSmsAgain(fragmentActivity: FragmentActivity){
        PhoneAuthProvider.getInstance()
            .verifyPhoneNumber(
                phone,
                AppConstants.SMS_RESEND_TIMEOUT,
                TimeUnit.SECONDS,
                fragmentActivity,
                smsCallback,
                resentToken
            )
    }

    private val smsCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            viewState?.showProgressBar(false)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            e.printStackTrace()
            viewState?.showMessage(resourceManager.getString(R.string.something_go_wrong))
            viewState?.showProgressBar(false)
        }

        override fun onCodeSent(storeVerificationId: String, resentToken: PhoneAuthProvider.ForceResendingToken) {
            this@SmsPresenter.storeVerificationId = storeVerificationId
            this@SmsPresenter.resentToken = resentToken
            viewState?.showMessage(resourceManager.getString(R.string.sign_sms_sent))
            viewState?.showProgressBar(false)
        }
    }

}