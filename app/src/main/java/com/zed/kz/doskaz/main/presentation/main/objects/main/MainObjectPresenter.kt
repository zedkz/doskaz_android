package com.zed.kz.doskaz.main.presentation.main.objects.main

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.First
import com.zed.kz.doskaz.entity.PhotoRequest
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class MainObjectPresenter(
    private val objectId: Int,
    private val resourceManger: ResourceManager,
    private val objectInteractor: ObjectInteractor
): BasePresenter<MainObjectFragmentView>(){
    
    private val photoDataList: MutableList<String> = mutableListOf()
    private val photoRequest = PhotoRequest()

    fun refreshObject(state: String){
        getObjectById(objectId, state)
    }

    fun onFirstInit(){
        getObjectById(objectId)
        photoDataList.add("")
        viewState?.showPhotoData(photoDataList)
    }

    private fun getObjectById(objectId: Int, state: String = ""){
        viewState?.showProgressBar(true)
        objectInteractor.getObjectById(objectId)
            .subscribe(
                {
                    it.id = objectId
                    LocalStorage.setCurrentObjectItem(it)
                    viewState?.showObjectInfo(it)

                    when(state){
                        MainObjectBottomSheetFragment.STATE_REVIEW -> viewState?.openReviewObjectFragment()
                        else -> viewState?.openDescriptionObjectFragment()
                    }
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun setImages(paths: List<String>){
        photoDataList.addAll(paths)
        viewState?.showPhotoData(photoDataList)
    }

    fun onCreateReviewBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openCreateObjectReviewFragment()
        else
            viewState?.openSignInFragment()
    }

    fun onUploadBtnClicked(){
        if (photoDataList.isNullOrEmpty()) return
        viewState?.showLocalProgressBar(true)
        uploadPhoto(1)
    }

    private fun uploadPhoto(position: Int){
        if (position == 0) photoRequest.photos.clear()
        Timber.i("PHOTOOO=${photoDataList.size}")
        if (photoDataList.size <= 1) return
        objectInteractor.upload(photoDataList[position])
            .subscribe(
                {
                    it.path?.let { it1 -> photoRequest.photos.add(it1) }
                    if (position < (photoDataList.size - 1)){
                        uploadPhoto(position + 1)
                    }else{
                        uploadObjectPhotos()
                    }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun uploadObjectPhotos(){
        objectInteractor.uploadObjectPhotos(
            objectId,
            photoRequest
        ).subscribe(
            {
                viewState?.showMessage(resourceManger.getString(R.string.photo_send_to_moderator))
                viewState?.showLocalProgressBar(false)
                photoDataList.clear()
                photoDataList.add("")
                photoRequest.photos.clear()
                viewState?.showPhotoData(photoDataList)
                viewState?.hideChoosePhotoBottomSheetBehavior()
            },
            {
                it.printStackTrace()
                viewState?.showLocalProgressBar(false)
            }
        ).connect()
    }

}