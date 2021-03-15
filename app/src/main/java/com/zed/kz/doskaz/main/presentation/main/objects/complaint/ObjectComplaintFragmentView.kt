package com.zed.kz.doskaz.main.presentation.main.objects.complaint

import com.zed.kz.doskaz.entity.CreateComplaint
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.Option
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ObjectComplaintFragmentView : BaseMvpView{

    fun showOptionsData(dataList: List<Option>)
    fun showVideoData(dataList: List<String>)
    fun showPhotoData(dataList: List<String>)
    fun openListDialogFragment(dataList: List<ListItem>)
    fun showCityTitle(title: String)
    fun showCityComplaintTitle(title: String)
    fun showAuthorityTitle(title: String)
    fun showTypeTitle(title: String)
    fun showDateTitle(date: String)
    fun closeThisFragmentWithResult(docId: Int)
    fun showOptions(show: Boolean)
    fun showComplaintProgressBar(show: Boolean)
    fun showCreatedComplaintInfo(createComplaint: CreateComplaint)
}