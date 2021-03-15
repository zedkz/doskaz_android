package com.zed.kz.doskaz.main.presentation.auth.sign_in

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AuthCredential
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants.SMS_RESEND_TIMEOUT
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.AuthInteractor
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

@InjectViewState
class SignInPresenter(
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor
): BasePresenter<SignInFragmentView>(){

    private var phone: String = ""

    fun onNextBtnClicked(phone: String?, fragmentActivity: FragmentActivity){

        Timber.i("PHH=$phone")
        if (phone.isNullOrEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.warning_empty_phone))
            return
        }

        if (phone.length != 9){
            viewState?.showMessage(resourceManager.getString(R.string.warning_wrong_phone))
            return
        }

        sendSmsViaFirebase("+77$phone", fragmentActivity)
    }

    private fun sendSmsViaFirebase(phone: String, fragmentActivity: FragmentActivity){
        viewState?.showProgressBar(true)
        this.phone = phone

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phone)
            .setTimeout(SMS_RESEND_TIMEOUT, TimeUnit.SECONDS)
            .setActivity(fragmentActivity)
            .setCallbacks(smsCallback)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
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
            viewState?.openSmsFragment(phone, storeVerificationId, resentToken)
            viewState?.showProgressBar(false)
        }
    }

    fun googleAuth(data: Intent?){
        viewState?.showProgressBar(true)
        try {
            GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnCompleteListener {
                    it.result?.idToken?.let { onSuccessGoogleAuth(it) }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
        }catch (e: ApiException){
            e.printStackTrace()
            viewState?.showProgressBar(false)
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
                        viewState?.openBonusFragment()
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

    fun onVkAuthClicked(activity: FragmentActivity){
        VK.login(activity, listOf(VKScope.EMAIL, VKScope.OFFLINE))
    }

    fun onSuccessGoogleAuth(token: String){
        viewState?.showProgressBar(true)
        authInteractor.authGoogle(User(token = token))
            .subscribe(
                {
                    onSuccessAuth(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onSuccessFacebookAuth(token: String){
        viewState?.showProgressBar(true)
        authInteractor.authFacebook(User(token = token))
            .subscribe(
                {
                    onSuccessAuth(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onSuccessVkAuth(token: String){
        viewState?.showProgressBar(true)
        authInteractor.authVk(User(token = token))
            .subscribe(
                {
                    onSuccessAuth(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onSuccessMailruAuth(token: String){
        viewState?.showProgressBar(true)
        authInteractor.authMailru(User(token = token))
            .subscribe(
                {
                    onSuccessAuth(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    private fun onSuccessAuth(response: Response<User>){
        if (response.code() == 201){
            LocalStorage.setUser(response.body())
            response.body()?.token?.let { token -> LocalStorage.setAccessToken(token) }
            viewState?.openBonusFragment()
        }else if (response.code() == 200){
            LocalStorage.setUser(response.body())
            response.body()?.token?.let { token -> LocalStorage.setAccessToken(token) }
            viewState?.openMainFragment()
        }
    }
}