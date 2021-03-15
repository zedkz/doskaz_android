package com.zed.kz.doskaz.main.presentation.profile.edit

import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.global.utils.MultipartHelper
import com.zed.kz.doskaz.main.data.interactor.UserInteractor
import java.util.concurrent.TimeUnit

@InjectViewState
class EditProfilePresenter(
    private val userInteractor: UserInteractor,
    private val resourceManager: ResourceManager
) : BasePresenter<EditProfileFragmentView>(){

    private var currentUser: User? = null
    private var storeVerificationId: String? = null

    fun onFirstInit(){
        getProfile()
    }

    private fun getProfile(){
        viewState?.showProgressBar(true)
        userInteractor.getProfile()
            .subscribe(
                {
                    currentUser = it
                    viewState?.showUserInfo(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun editProfile(
        lastName: String,
        firstName: String,
        middleName: String,
        email: String,
        phone: String,
        status: String,
        sms: String
    ){
        if (lastName.isNotEmpty()) currentUser?.lastName = lastName else currentUser?.lastName = null
        if (firstName.isNotEmpty()) currentUser?.firstName = firstName else currentUser?.firstName = null
        if (middleName.isNotEmpty()) currentUser?.middleName = middleName else currentUser?.middleName = null
        if (email.isNotEmpty()) currentUser?.email = email else currentUser?.email = null
        if (sms.isNotEmpty()) if (phone.isNotEmpty()) currentUser?.newPhone = "+77$phone" else currentUser?.newPhone = null
        if (status.isNotEmpty()) currentUser?.status = status else currentUser?.status = null

        viewState?.showProgressBar(true)

        if (sms.isEmpty()){
            updateProfile()
        }else{
            smsValidation(sms)
        }

    }

    private fun updateProfile(){
        currentUser?.let {
            val pureUser = User()
            pureUser.lastName = currentUser?.lastName
            pureUser.middleName = currentUser?.middleName
            pureUser.firstName = currentUser?.firstName
            pureUser.email = currentUser?.email
            pureUser.phone = currentUser?.newPhone
            pureUser.phoneChangeToken = currentUser?.phoneChangeToken

            userInteractor.editProfile(pureUser)
                .subscribe(
                    {
                        LocalStorage.setUser(it)
                        if (it.imagePath == null){
                            viewState?.showMessage(resourceManager.getString(R.string.profile_updated))
                            viewState?.showProgressBar(false)
                            viewState?.showUserInfo(it)
                        }else{
                            editProfileAvatar(it.imagePath)
                        }
                    },
                    {
                        it.printStackTrace()
                        viewState?.showMessage(it.getObjectErrorMessage())
                        viewState?.showProgressBar(false)
                    }
                ).connect()
        }
    }

    private fun editProfileAvatar(path: String?){
        MultipartHelper.getFileDataFromPath(path)?.let {
            userInteractor.editProfileAvatar(it)
                .subscribe(
                    {
                        currentUser?.let { viewState?.showUserInfo(it) }
                        viewState?.showMessage(resourceManager.getString(R.string.profile_updated))
                        viewState?.showProgressBar(false)
                    },
                    {
                        it.printStackTrace()
                    }
                ).connect()
        }

    }

    fun setImagePath(path: String){
        currentUser?.imagePath = path
    }

    fun onGetSmsBtnClicked(fragmentActivity: FragmentActivity){
        currentUser?.phone?.let {
            viewState?.showProgressBar(true)
            PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(
                    it,
                    AppConstants.SMS_RESEND_TIMEOUT,
                    TimeUnit.SECONDS,
                    fragmentActivity,
                    smsCallback
                )
        }
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
            this@EditProfilePresenter.storeVerificationId = storeVerificationId
            viewState?.showMessage(resourceManager.getString(R.string.profile_sms_send))
            viewState?.showProgressBar(false)
        }
    }

    private fun smsValidation(sms: String){
        storeVerificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, sms)

            FirebaseAuth.getInstance()
                .signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        task.result
                            ?.user
                            ?.getIdToken(true)
                            ?.addOnCompleteListener {
                                it.result?.token?.let { idToken ->
                                    currentUser?.phoneChangeToken = idToken
                                    updateProfile()
                                }
                            }
                    }else{
                        viewState?.showMessage(resourceManager.getString(R.string.warning_wrong_code))
                        viewState?.showProgressBar(false)
                    }
                }
        }
    }

}