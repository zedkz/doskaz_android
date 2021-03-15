package com.zed.kz.doskaz.main.presentation.main.objects.main

import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MainObjectFragmentView : BaseMvpView{

    fun showObjectInfo(objectItem: ObjectItem)
    fun openDescriptionObjectFragment()
    fun openPhotoObjectFragment()
    fun openReviewObjectFragment()
    fun openVideoObjectFragment()
    fun openHistoryObjectFragment()
    fun openCreateObjectReviewFragment()
    fun openSignInFragment()
    fun showPhotoData(dataList: MutableList<String>)
    fun hideChoosePhotoBottomSheetBehavior()
    fun showLocalProgressBar(show: Boolean)

}