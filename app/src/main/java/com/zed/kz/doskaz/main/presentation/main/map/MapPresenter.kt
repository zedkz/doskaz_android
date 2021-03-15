package com.zed.kz.doskaz.main.presentation.main.map

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.zed.kz.doskaz.entity.MapSettings
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class MapPresenter(
    private val listInteractor: ListInteractor,
    private val objectInteractor: ObjectInteractor
): BasePresenter<MapFragmentView>(){

    var firstBound: LatLng? = null
    var secondBound: LatLng? = null
    var currentBounds: LatLngBounds? = null
    private var currentCityId: Int? = null
    private var subCategoryId: Int? = null
    private var accessibleParams: Map<String, String>? = null
    private var currentPoints: List<ObjectItem>? = listOf()

    fun onFirstInit(){
        if (LocalStorage.getCurrentCity() == null) {
            initDetectCity()
        }else{
            currentCityId = LocalStorage.getCurrentCity()?.id
            LocalStorage.getCurrentCity()?.bounds?.let { bounds ->
                firstBound = LatLng(bounds[0][0], bounds[0][1])
                secondBound = LatLng(bounds[1][0], bounds[1][1])
            }
            Handler().postDelayed({ viewState?.boundsReady() }, 2000)
        }
    }

    private fun initDetectCity(){
        viewState?.showProgressBar(true)
        listInteractor.detectCity()
            .subscribe(
                {
                    currentCityId = it.id
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

    fun getObjectsOnMap(zoom: Float?, bounds: LatLngBounds?, query: String? = null){
        currentBounds = bounds
        MapSettings.currentZoom = zoom?.toInt()
        MapSettings.currentBounds = "${bounds?.southwest?.latitude},${bounds?.southwest?.longitude},${bounds?.northeast?.latitude},${bounds?.northeast?.longitude}"
        objectInteractor.getObjectsOnMap(
            MapSettings.currentZoom ?: 0,
            MapSettings.currentBounds ?: "",
            query
        ).subscribe(
            {
                it.points?.let { it1 ->
                    it.clusters?.let { it2 ->
                        viewState?.showObjectsOnMap(it1, it2)
                    }
                }
                viewState?.showProgressBar(false)
            },
            {
                it.printStackTrace()
                viewState?.showProgressBar(false)
            }
        ).connect()
    }

    fun onMapCameraMoveFinished(zoom: Float?, bounds: LatLngBounds?){
        if (subCategoryId == null && accessibleParams == null){
            getObjectsOnMap(zoom, bounds)
        }
    }

    fun onSearchBtnClicked(){
        currentCityId?.let { viewState?.openSearchFragment(it) }
    }

    fun onFilterBtnClicked(){
        viewState?.openFilterFragment()
    }

    fun onSearchFragmentClosed(query: String?){
        query?.let {
            viewState?.showSearchTitle(query)
            getObjectsOnMap(
                MapSettings.currentZoom?.toFloat(),
                currentBounds,
                LocalStorage.getCurrentQuery()
            )
        }
    }

    fun onFilterFragmentResult(params: Map<String, String>?){
        this.subCategoryId = subCategoryId
        this.accessibleParams = params
        if (params != null){
            viewState?.showProgressBar(true)
            objectInteractor.getObjectsOnMap(
                zoom = MapSettings.currentZoom ?: 0,
                bbox = MapSettings.currentBounds ?: "",
                query = LocalStorage.getCurrentQuery(),
                accessibilityLevels = params
            ).subscribe(
                {
                    currentPoints = it.points
                    it.points?.let { it1 ->
                        it.clusters?.let { it2 ->
                            viewState?.showObjectsOnMap(it1, it2)
                        }
                    }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
        }else{
            initDetectCity()
        }
    }

    fun onMarkerClicked(tag: Any?){
        if (tag is ObjectItem){
            tag.id?.let { viewState?.openMainObjectFragment(it) }
        }
    }

    fun onCreateObjectBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL){
            viewState?.openAddSimpleObjectFragment()
        }else{
            viewState?.openSignInFragment()
        }
    }

    fun onCreateComplaintBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL){
            viewState?.openObjectComplaintFragment()
        }else{
            viewState?.openSignInFragment()
        }
    }

    fun onSearchFragmentItemSelected(objectItem: ObjectItem){
        objectItem.id?.let {
            getObjectById(it)
            viewState?.openMainObjectFragment(it)
        }
    }

    private fun getObjectById(id: Int){
        objectInteractor.getObjectById(id)
            .subscribe(
                {
                    viewState?.selectMarker(it)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }
}