package com.zed.kz.doskaz.main.presentation.main.objects.description

import com.zed.kz.doskaz.global.base.BaseMvpView

interface DescriptionObjectFragmentView : BaseMvpView{

    fun showDescription(description: String)
    fun showVerifiedText(text: String)
    fun openObjectVerificationDialogFragment(objectName: String)
    fun openObjectComplaintFragment(objectId: Int)
    fun openSignInFragment()
    fun openObjectInfoCategoryFragment()
    fun openObjectReviewDialogFragment()
    fun openCreateReviewDialogFragment()

}