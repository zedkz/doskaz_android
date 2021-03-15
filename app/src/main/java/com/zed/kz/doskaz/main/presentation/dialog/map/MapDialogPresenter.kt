package com.zed.kz.doskaz.main.presentation.dialog.map

import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor

@InjectViewState
class MapDialogPresenter(
    private val listInteractor: ListInteractor
): BasePresenter<MapDialogFragmentView>(){

    var firstBound: LatLng? = null
    var secondBound: LatLng? = null
    var currentLatLng: LatLng? = null
    var currentAddress: String = ""

    fun onFirstInit(){
        if (LocalStorage.getCurrentCity() == null)
            detectCity()
        else {
            LocalStorage.getCurrentCity()?.bounds?.let { bounds ->
                firstBound = LatLng(bounds[0][0], bounds[0][1])
                secondBound = LatLng(bounds[1][0], bounds[1][1])
            }
            viewState?.boundsReady()
        }
    }

    private fun detectCity(){
        viewState?.showProgressBar(true)
        listInteractor.detectCity()
            .subscribe(
                {
                    it.bounds?.let { bounds ->
                        firstBound = LatLng(bounds[0][0], bounds[0][1])
                        secondBound = LatLng(bounds[1][0], bounds[1][1])
                    }
                    viewState?.boundsReady()
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onMapClicked(latLng: LatLng){
        currentLatLng = latLng
        listInteractor.getAddressFromLatLng("${latLng.longitude},${latLng.latitude}")
            .subscribe(
                {
                    it.response
                        ?.geoObjectCollection
                        ?.featureMember
                        ?.let {
                            if (it.isNotEmpty()){
                                var address = it.first()
                                    .geoObject
                                    ?.metaDataProperty
                                    ?.geocoderMetaData
                                    ?.text ?: ""
                                address = address.replace("Казахстан, ", "")
                                viewState?.showAddress(address)
                                currentAddress = address
                            }
                        }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onReadyBtnClicked(){
        currentLatLng?.let {
            viewState?.closeThisFragmentWithResult(
                it,
                currentAddress
            )
        }
    }

}