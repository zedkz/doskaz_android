package com.zed.kz.doskaz.main.presentation.main.objects.description

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor

@InjectViewState
class DescriptionObjectPresenter(
    private val resourceManager: ResourceManager,
    private val objectInteractor: ObjectInteractor
): BasePresenter<DescriptionObjectFragmentView>(){

    private var currentObjectItem: ObjectItem? = null

    fun onFirstInit(){
        currentObjectItem = LocalStorage.getCurrentObjectItem()
        currentObjectItem?.let {
            viewState?.showDescription(it.description ?: "")
        }

        when(currentObjectItem?.verificationStatus){
            AppConstants.VERIFIED_FULL -> viewState?.showVerifiedText(resourceManager.getString(R.string.full_verified))
            AppConstants.VERIFIED_PARTIALLY -> viewState?.showVerifiedText(resourceManager.getString(R.string.partially_verified))
            AppConstants.VERIFIED_NOT -> viewState?.showVerifiedText(resourceManager.getString(R.string.not_verified))
        }
    }

    fun showMoreBtnClicked(){
        viewState?.openObjectInfoCategoryFragment()
    }

    fun onVerificationBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            currentObjectItem?.title?.let { viewState?.openObjectVerificationDialogFragment(it) }
        else
            viewState?.openSignInFragment()
    }

    fun onComplaintBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            currentObjectItem?.id?.let { viewState?.openObjectComplaintFragment(it) }
        else
            viewState?.openSignInFragment()
    }

    fun objectVerification(status: String){
        objectInteractor.objectVerification(
            currentObjectItem?.id ?: 0,
            status
        ).subscribe(
            {
                if (status == AppConstants.VERIFICATION_STATUS_CONFIRM)
                    viewState?.showMessage(resourceManager.getString(R.string.om_verification_thanks))
                else
                    viewState?.openObjectReviewDialogFragment()
            },
            {
                it.printStackTrace()
                viewState?.showMessage(it.getObjectErrorMessage())
            }
        ).connect()
    }

}