package com.zed.kz.doskaz.main.presentation.dialog.map

import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MapDialogFragmentView : BaseMvpView{

    fun boundsReady()

    fun showAddress(address: String)

    fun closeThisFragmentWithResult(latLng: LatLng, address: String)

}