package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.common

import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.entity.First
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor

@InjectViewState
class ObjectAddCommonPresenter(
    private val resourceManager: ResourceManager,
    private val objectInteractor: ObjectInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<ObjectAddCommonFragmentView>(){

    private var createObjectMediumHard: CreateObjectMediumHard? = null
    private val videoDataList: MutableList<String> = mutableListOf()
    private val photoDataList: MutableList<String> = mutableListOf()
    private var categoryList: List<Category> = listOf()
    private var currentSelectedCategory: Category? = null

    fun onFirstInit(){
        createObjectMediumHard = CreateObjectMediumHard.getInstance()
        createObjectMediumHard?.first = First()

        videoDataList.add("")
        viewState?.showVideoData(videoDataList)

        photoDataList.add("")
        viewState?.showPhotoData(photoDataList)

        getCategories()
    }

    fun onAddVideoItemClicked(){
        videoDataList.add("")
        viewState?.showVideoData(videoDataList)
    }

    fun onRemoveVideoItemClicked(position: Int){
        videoDataList.removeAt(position)
        viewState?.showVideoData(videoDataList)
    }

    fun addPickedImage(paths: List<String>){
        paths.forEach {
            photoDataList.add(it)
            viewState?.showPhotoData(photoDataList)
            uploadPhoto(it)
        }
    }

    fun onMapBtnClicked(){
        viewState?.openMapDialogFragment()
    }

    private fun uploadPhoto(path: String?){
        viewState?.showProgressBar(true)
        objectInteractor.upload(path)
            .subscribe(
                {
                    it?.path?.let { createObjectMediumHard?.first?.photos?.add(it) }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onListItemSelected(listItem: ListItem) {
        val title = listItem.title ?: ""
        when(listItem.requestCode) {
            AppConstants.RC_CATEGORY -> {
                categoryList.forEach { if (it.id == listItem.id) currentSelectedCategory = it }
                viewState?.showCategoryTitle(title)
            }
            AppConstants.RC_SUB_CATEGORY -> {
                createObjectMediumHard?.first?.categoryId = listItem.id
                viewState?.showSubCategoryTitle(title)
            }
        }
    }

    fun onMapPositionSelected(latLng: LatLng, address: String){
        viewState?.showMapLatLngAndAddress(latLng, address)
        createObjectMediumHard?.first?.point?.addAll(
            listOf(
                latLng.latitude,
                latLng.longitude
            )
        )
    }

    private fun getCategories(){
        listInteractor.getCategories()
            .subscribe(
                {
                    categoryList = it
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onCategoryBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        categoryList.forEach {
            dataList.add(
                ListItem(
                    id = it.id,
                    title = it.title,
                    requestCode = AppConstants.RC_CATEGORY
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onSubCategoryBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        categoryList.forEach {
            if (it.id == currentSelectedCategory?.id){
                it.subCategories?.forEach {
                    dataList.add(
                        ListItem(
                            id = it.id,
                            title = it.title,
                            requestCode = AppConstants.RC_SUB_CATEGORY
                        )
                    )
                }
            }
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onReadyBtnClicked(
        name: String,
        otherName: String,
        address: String,
        desc: String
    ){
        if (createObjectMediumHard?.first?.photos.isNullOrEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.object_upload_photo))
            return
        }

        createObjectMediumHard?.apply {
            first?.name = name
            first?.otherNames = otherName
            first?.address = address
            first?.description = desc
        }

        videoDataList.filter { it.isNotEmpty() }.forEach {
            createObjectMediumHard?.first?.videos
        }

        viewState?.closeThisFragmentWithResult()
    }

}