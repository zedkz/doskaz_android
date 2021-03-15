package com.zed.kz.doskaz.main.presentation.main.objects.photo

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Photo
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class PhotoObjectPresenter : BasePresenter<PhotoObjectFragmentView>(){

    fun onFirstInit(){
        LocalStorage.getCurrentObjectItem().photos?.let { viewState?.showPhotos(it) }
    }

    fun onPhotoItemClicked(photo: Photo){
        photo.previewUrl?.let { viewState?.openImageFragment(it) }
    }
}