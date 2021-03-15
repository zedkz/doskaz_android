package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.common

import android.net.Uri
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.base.PhotoBaseDialogFragment
import com.zed.kz.doskaz.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogFragment
import com.zed.kz.doskaz.main.presentation.dialog.map.MapDialogFragment
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic.ObjectAddDynamicFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.PhotoAdapter
import com.zed.kz.doskaz.main.presentation.objects.add.simple.VideoAdapter
import kotlinx.android.synthetic.main.fragment_add_object_common.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ObjectAddCommonFragment(val OnCloseWithResult: () -> Unit): PhotoBaseDialogFragment(), ObjectAddCommonFragmentView{

    companion object{

        val TAG = "ObjectAddCommonFragment"

        fun newInstance(OnCloseWithResult: () -> Unit): ObjectAddCommonFragment =
            ObjectAddCommonFragment(OnCloseWithResult)

    }

    @InjectPresenter
    lateinit var presenter: ObjectAddCommonPresenter

    private val videoAdapter = VideoAdapter(
        { presenter.onAddVideoItemClicked() },
        { presenter.onRemoveVideoItemClicked(it) }
    )

    private val photoAdapter = PhotoAdapter(
        { pickPhotoFromGalleryWithPermissionCheck(false) },
        {  }
    )

    @ProvidePresenter
    fun providePresenter(): ObjectAddCommonPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_COMMON_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.OBJECT_ADD_COMMON_SCOPE, named(MainScope.OBJECT_ADD_COMMON_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_add_object_common

    override fun setUp(savedInstanceState: Bundle?) {

        rvVideoAOC?.adapter = videoAdapter
        rvPhotoAOC?.adapter = photoAdapter
        presenter.onFirstInit()
        ovMapAOC?.setOnClickListener { presenter.onMapBtnClicked() }
        ovCategoryAOC?.setOnClickListener { presenter.onCategoryBtnClicked() }
        ovSubCategoryAOC?.setOnClickListener { presenter.onSubCategoryBtnClicked() }
        imgBackToolbarMain?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.object_common_info)
        }
        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener {
                if (isDataValid()){
                    presenter.onReadyBtnClicked(
                        name = ovetNameAOC.getValue(),
                        otherName = ovetOtherNameAOC.getValue(),
                        address = ovetAddressAOC.getValue(),
                        desc = obetDescAOC.getValue()
                    )
                }
            }
        }
    }

    private fun isDataValid(): Boolean{

        if (ovetNameAOC.isDataValid() &&
            ovetOtherNameAOC.isDataValid() &&
            ovetAddressAOC.isDataValid() &&
            ovCategoryAOC.isDataValid() &&
            ovSubCategoryAOC.isDataValid() &&
            ovMapAOC.isDataValid() &&
            obetDescAOC.isDataValid()){
            return true
        }

        return false
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_COMMON_SCOPE)
        super.onDestroy()
    }

    override fun setImage(uri: Uri) {

    }

    override fun setImagePath(path: String) {

    }

    override fun setImagePaths(paths: List<String>) {
        presenter.addPickedImage(paths)
    }

    override fun setVideo(uri: Uri) {

    }

    override fun setVideoPath(path: String) {

    }

    override fun showVideoData(dataList: MutableList<String>) {
        videoAdapter.submitData(dataList)
    }

    override fun showPhotoData(dataList: MutableList<String>) {
        photoAdapter.submitData(dataList)
    }

    override fun openListDialogFragment(dataList: List<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(ArrayList(dataList))
            { presenter.onListItemSelected(it) }.show(it, ListDialogFragment.TAG)
        }
    }

    override fun openMapDialogFragment() {
        activity?.supportFragmentManager?.let {
            MapDialogFragment.newInstance { latLng, address -> presenter.onMapPositionSelected(latLng, address)  }
                .show(it, MapDialogFragment.TAG)
        }
    }

    override fun showMapLatLngAndAddress(latLng: LatLng, address: String) {
        ovMapAOC?.setValue("${latLng.latitude}, ${latLng.longitude}")
        ovetAddressAOC?.setValue(address)
    }

    override fun showCategoryTitle(title: String) {
        ovCategoryAOC?.setValue(title)
    }

    override fun showSubCategoryTitle(title: String) {
        ovSubCategoryAOC?.setValue(title)
    }

    override fun closeThisFragmentWithResult() {
        OnCloseWithResult.invoke()
        activity?.onBackPressed()
    }
}