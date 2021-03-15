package com.zed.kz.doskaz.main.presentation.dialog.map

import android.os.Bundle
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.dialog_map_fragment.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MapDialogFragment(val onItemSelectedListener: (LatLng, String) -> Unit): BaseDialogFragment(), MapDialogFragmentView, OnMapReadyCallback{

    companion object{

        val TAG = "MapDialogFragment"

        fun newInstance(onItemSelectedListener: (LatLng, String) -> Unit): MapDialogFragment =
            MapDialogFragment(onItemSelectedListener)
    }

    @InjectPresenter
    lateinit var presenter: MapDialogPresenter

    @ProvidePresenter
    fun providePresenter(): MapDialogPresenter {
        getKoin().getScopeOrNull(MainScope.MAP_DIALOG_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.MAP_DIALOG_SCOPE, named(MainScope.MAP_DIALOG_SCOPE))
        return scope.get()
    }

    override val layoutRes: Int
        get() = R.layout.dialog_map_fragment

    private var marker: Marker? = null

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit()
        mapViewMapDialog?.onCreate(savedInstanceState)
        btnReadyMapDialog?.setOnClickListener {
            presenter.onReadyBtnClicked()
        }
    }

    override fun boundsReady() {
        mapViewMapDialog?.apply {
            getMapAsync(this@MapDialogFragment)
            onResume()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MAP_DIALOG_SCOPE)
        super.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.apply {
            moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    LatLngBounds(presenter.firstBound, presenter.secondBound),
                    AppConstants.MAP_ZOOM
                )
            )

            setOnMapClickListener {
                clear()
                marker = addMarker(MarkerOptions().position(it))
                presenter.onMapClicked(it)
            }
        }
    }

    override fun showAddress(address: String) {
        txtAddressMapDialog.text = address
    }

    override fun closeThisFragmentWithResult(latLng: LatLng, address: String) {
        onItemSelectedListener.invoke(latLng, address)
        dismiss()
    }

}