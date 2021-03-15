package com.zed.kz.doskaz.main.presentation.objects.add.simple

import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.SimpleObjectItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface AddSimpleObjectFragmentView : BaseMvpView{

    fun showVideoData(dataList: MutableList<String>)
    fun showPhotoData(dataList: MutableList<String>)
    fun openListDialogFragment(dataList: List<ListItem>)
    fun openMapDialogFragment()
    fun showMapLatLngAndAddress(latLng: LatLng, address: String)
    fun showCategoryTitle(title: String)
    fun showSubCategoryTitle(title: String)
    fun showSimpleDataList(dataList: MutableList<SimpleObjectItem>)
    fun closeThisFragment()

}