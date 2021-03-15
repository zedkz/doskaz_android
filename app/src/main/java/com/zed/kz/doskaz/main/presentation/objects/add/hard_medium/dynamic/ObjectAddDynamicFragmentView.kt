package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic

import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ObjectAddDynamicFragmentView : BaseMvpView{

    fun showUIData(dataList: List<AddObject>)
    fun updateAdapter()
    fun openListDialogFragment(dataList: List<ListItem>)
    fun closeThisFragmentWithResult()
    fun showTitle(title: String)
    fun showAvailabilityImages(
        drawable1: Int,
        drawable2: Int,
        drawable3: Int,
        drawable4: Int,
        drawable5: Int
    )
    fun showAvailabilityTitle(title: String)

}