package com.zed.kz.doskaz.main.presentation.objects.add.simple

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.SimpleObjectItem
import com.zed.kz.doskaz.global.base.PhotoBaseFragment
import com.zed.kz.doskaz.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogFragment
import com.zed.kz.doskaz.main.presentation.dialog.map.MapDialogFragment
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.category.ObjectAddCategoryFragment
import kotlinx.android.synthetic.main.fragment_add_simple_object.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AddSimpleObjectBottomSheetFragment : PhotoBaseFragment(), AddSimpleObjectFragmentView{

    companion object{

        val TAG = "AddSimpleObjectFragment"

        fun newInstance(): AddSimpleObjectBottomSheetFragment =
            AddSimpleObjectBottomSheetFragment()
    }

    @InjectPresenter
    lateinit var presenter: AddSimpleObjectPresenter

    @ProvidePresenter
    fun providePresenter(): AddSimpleObjectPresenter {
        getKoin().getScopeOrNull(MainScope.ADD_SIMPLE_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.ADD_SIMPLE_OBJECT_SCOPE, named(MainScope.ADD_SIMPLE_OBJECT_SCOPE)).get()
    }

    private val videoAdapter = VideoAdapter(
        { presenter.onAddVideoItemClicked() },
        { presenter.onRemoveVideoItemClicked(it) }
    )

    private val photoAdapter = PhotoAdapter(
        { pickPhotoFromGalleryWithPermissionCheck(false) },
        {  }
    )

    private val simpleObjectItemAdapter = SimpleObjectAdapter{ simpleObjectItem, addObject ->
        presenter.onSimpleObjectItemSelected(simpleObjectItem, addObject)
    }

    override val layoutRes: Int
        get() = R.layout.fragment_add_simple_object

    override fun setUp(savedInstanceState: Bundle?) {
        txtTitleToolbarMain?.text = getString(R.string.add_object)

        imgBackToolbarMain?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }

        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener {
                if (isDataValid()) {
                    presenter.onReadyBtnClicked(
                        name = ovetNameASO.getValue(),
                        otherName = ovetOtherNameASO.getValue(),
                        address = ovetAddressASO.getValue(),
                        description = ovetDescASO.getValue()
                    )
                }
            }
        }

        imgNextASO?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(
                TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            activity?.addFragmentWithBackStack(
                R.id.container,
                ObjectAddCategoryFragment.newInstance(AppConstants.FORM_TYPE_MIDDLE),
                ObjectAddCategoryFragment.getInstance()?.TAG ?: ""
            )
        }

        imgPreviousASO?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(
                TAG,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            activity?.addFragmentWithBackStack(
                R.id.container,
                ObjectAddCategoryFragment.newInstance(AppConstants.FORM_TYPE_FULL),
                ObjectAddCategoryFragment.getInstance()?.TAG ?: ""
            )
        }

        rvVideoASO?.adapter = videoAdapter
        rvPhotoASO?.adapter = photoAdapter
        recyclerAddSimpleObject?.adapter = simpleObjectItemAdapter
        presenter.onFirstInit()
        ovMapASO?.setOnClickListener { presenter.onMapBtnClicked() }
        ovCategoryASO?.setOnClickListener { presenter.onCategoryBtnClicked() }
        ovSubCategoryASO?.setOnClickListener { presenter.onSubCategoryBtnClicked() }
    }

    fun isDataValid(): Boolean{
        if (ovetNameASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovetOtherNameASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovetAddressASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovCategoryASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovSubCategoryASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovMapASO.isDataValid(scrollViewASO, rootConstASO) &&
            ovetDescASO.isDataValid(scrollViewASO, rootConstASO)){
            return true
        }

        return false
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.ADD_SIMPLE_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showVideoData(dataList: MutableList<String>) {
        videoAdapter.submitData(dataList)
    }

    override fun showPhotoData(dataList: MutableList<String>) {
        photoAdapter.submitData(dataList)
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

    override fun openListDialogFragment(dataList: List<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(ArrayList(dataList))
            { presenter.onListItemSelected(it) }.show(it, ListDialogFragment.TAG)
        }
    }

    override fun openMapDialogFragment() {
        activity?.supportFragmentManager?.let {
            MapDialogFragment.newInstance { latLng, address ->  presenter.onMapPositionSelected(latLng, address) }
                .show(it, MapDialogFragment.TAG)
        }
    }

    override fun showCategoryTitle(title: String) {
        ovCategoryASO.setValue(title)
    }

    override fun showSubCategoryTitle(title: String) {
        ovSubCategoryASO.setValue(title)
    }

    override fun showMapLatLngAndAddress(latLng: LatLng, address: String) {
        ovMapASO.setValue("${latLng.latitude}, ${latLng.longitude}")
        ovetAddressASO?.setValue(address)
    }

    override fun closeThisFragment() {
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .sendBroadcast(Intent(AppConstants.FILTER_OBJECT_CREATED))
        }
        activity?.onBackPressed()
    }

    override fun showSimpleDataList(dataList: MutableList<SimpleObjectItem>) {
        simpleObjectItemAdapter.submitData(dataList)
    }
}