package com.zed.kz.doskaz.main.presentation.main.map

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.collections.MarkerManager
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.IconicsSize
import com.mikepenz.iconics.utils.backgroundColorInt
import com.mikepenz.iconics.utils.padding
import com.mikepenz.iconics.utils.roundedCornersPx
import com.mikepenz.iconics.utils.sizePx
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Cluster
import com.zed.kz.doskaz.entity.ClusterIconRenderer
import com.zed.kz.doskaz.entity.MapSettings
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.filter.FilterFragment
import com.zed.kz.doskaz.main.presentation.main.objects.complaint.ObjectComplaintFragment
import com.zed.kz.doskaz.main.presentation.main.objects.main.MainObjectBottomSheetFragment
import com.zed.kz.doskaz.main.presentation.main.search.SearchFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.AddSimpleObjectBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber


class MapFragment : BaseFragment(), MapFragmentView, OnMapReadyCallback{

    companion object{

        val TAG = "MapFragment"

        fun newInstance(): MapFragment =
            MapFragment()
    }

    @InjectPresenter
    lateinit var presenter: MapPresenter

    @ProvidePresenter
    fun providePresenter(): MapPresenter {
        getKoin().getScopeOrNull(MainScope.MAP_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MAP_SCOPE, named(MainScope.MAP_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_map

    private var googleMap: GoogleMap? = null
    private var oldMarker: Marker? = null
    private var isSelected: Boolean = false
    private var clusterManager: ClusterManager<Cluster>? = null
    private var markerCollection : MarkerManager.Collection? = null

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit()
        txtSearchMap.setOnClickListener { presenter.onSearchBtnClicked() }
        imgFilterMap.setOnClickListener { presenter.onFilterBtnClicked() }
        fabCreateObjectMap.setOnClickListener { presenter.onCreateObjectBtnClicked() }
        fabCreateComplaintMap.setOnClickListener { presenter.onCreateComplaintBtnClicked() }
        mapViewMap?.apply {
            onCreate(savedInstanceState)
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MAP_SCOPE)
        super.onDestroy()
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        if (mapViewMap?.isShown ?: false){
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngBounds(
                LatLngBounds(presenter.firstBound, presenter.secondBound),
                AppConstants.MAP_ZOOM
            ))
            presenter.getObjectsOnMap(
                AppConstants.MAP_ZOOM.toFloat(),
                googleMap?.projection?.visibleRegion?.latLngBounds
            )
            googleMap?.setOnCameraIdleListener {
                MapSettings.currentZoom = googleMap.cameraPosition?.zoom?.toInt()

                if(!isSelected){
                    presenter.onMapCameraMoveFinished(
                        googleMap.cameraPosition?.zoom,
                        googleMap.projection?.visibleRegion?.latLngBounds
                    )
                }
                isSelected = false
            }

            clusterManager = ClusterManager(context, googleMap)
            clusterManager?.renderer = ClusterIconRenderer(context, googleMap, clusterManager)
            googleMap?.setOnMarkerClickListener(clusterManager?.markerManager)
            markerCollection = clusterManager?.markerManager?.newCollection()
            clusterManager?.setOnClusterClickListener {
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        it.position,
                        16f
                    )
                )
                true
            }

            clusterManager?.setOnClusterItemClickListener {
                googleMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        it.position,
                        18f
                    )
                )
                true
            }

            markerCollection?.setOnMarkerClickListener {
                isSelected = true
                presenter.onMarkerClicked(it.tag)
                if (oldMarker?.tag != null)
                    oldMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(getIcon(oldMarker?.tag as ObjectItem, false)))
                oldMarker = it
                if (oldMarker?.tag != null)
                    it.setIcon(BitmapDescriptorFactory.fromBitmap(getIcon(oldMarker?.tag as ObjectItem, true)))

                return@setOnMarkerClickListener true
            }
        }

    }

    override fun boundsReady() {
        mapViewMap?.onResume()
        mapViewMap?.getMapAsync(this@MapFragment)
    }

    override fun showObjectsOnMap(dataList: List<ObjectItem>, clusters: List<Cluster>) {
        googleMap?.apply {
            markerCollection?.markers?.forEach { it.remove() }

            dataList.forEach { objectItem ->
                markerCollection?.addMarker(
                    MarkerOptions().position(
                            LatLng(
                                objectItem.coordinates?.get(0) ?: 0.0,
                                objectItem.coordinates?.get(1) ?: 0.0
                            )
                        )
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(
                                getIcon(objectItem, false)
                            )
                        )
                    )?.apply {
                        tag = objectItem
                    }
            }

            clusterManager?.clearItems()
            clusters.forEach { cluster->
                clusterManager?.addItem(cluster)
            }
            clusterManager?.cluster()
        }
    }

    private fun getIcon(objectItem: ObjectItem, isScale: Boolean): Bitmap? {
        val icon = context?.let { IconicsDrawable(it, (objectItem.icon ?: "").replace("fa-", "faw-")) }
        icon?.setTint(Color.WHITE)

        val size = if(isScale) {
            icon?.sizePx = 30
            70
        }else {
            icon?.sizePx = 60
            icon?.roundedCornersPx = 5f
            icon?.paddingPx = 18
            icon?.backgroundColorInt = Color.parseColor(objectItem.color)
            50
        }

        if (isScale){
            return  context?.let {
                val drawable = ContextCompat.getDrawable(it, R.drawable.ic_107)
                drawable?.setTint(Color.parseColor(objectItem.color))
                overlay(drawable?.toBitmap(size, size, null), icon?.toBitmap())
            }
        }
        return icon?.toBitmap()
    }

    private fun overlay(bmp1: Bitmap?, bmp2: Bitmap?): Bitmap? {
        val bmOverlay = bmp1?.let { Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config) }
        val canvas = bmOverlay?.let { Canvas(it) }
        bmp1?.let { canvas?.drawBitmap(bmp1, Matrix(), null) }
        bmp2?.let { canvas?.drawBitmap(bmp2, 20f, 15f, null) }
        return bmOverlay
    }

    override fun openSearchFragment(cityId: Int) {
        activity?.addFragmentWithBackStack(
            R.id.container_home,
            SearchFragment.newInstance(
                cityId,
                { presenter.onSearchFragmentItemSelected(it) },
                { openFilterFragment() }
            ),
            SearchFragment.TAG
        )
    }

    override fun openFilterFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container_home,
            FilterFragment.newInstance{ params ->
                presenter.onFilterFragmentResult(params)
            },
            FilterFragment.TAG
        )
    }

    override fun openAddSimpleObjectFragment() {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            AddSimpleObjectBottomSheetFragment.newInstance(),
            AddSimpleObjectBottomSheetFragment.TAG
        )
    }

    override fun openSignInFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            SignInFragment.newInstance(),
            SignInFragment.TAG
        )
    }

    override fun openObjectComplaintFragment() {
        val objectComplaintFragment = ObjectComplaintFragment.newInstance()
        objectComplaintFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogFragmentTheme)
        activity?.supportFragmentManager?.let {
            objectComplaintFragment.show(it, ObjectComplaintFragment.TAG)
        }
    }

    override fun showSearchTitle(title: String) {
        txtSearchMap.text = title
    }

    override fun openMainObjectFragment(objectId: Int) {
        childFragmentManager.let {
            MainObjectBottomSheetFragment.newInstance(objectId)
                .show(it, MainObjectBottomSheetFragment.TAG)
        }

    }

    override fun selectMarker(objectItem: ObjectItem) {
        isSelected = true
        val latLng =  LatLng(
            objectItem.coordinates?.get(0) ?: 0.0,
            objectItem.coordinates?.get(1) ?: 0.0
        )
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                18f
            )
        )
        oldMarker = googleMap?.addMarker(MarkerOptions().position(latLng))
        oldMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(
            getIcon(objectItem, true)
        ))
    }

}