package com.zed.kz.doskaz.main.presentation.main.objects.photo

import com.zed.kz.doskaz.entity.Photo
import com.zed.kz.doskaz.global.base.BaseMvpView

interface PhotoObjectFragmentView : BaseMvpView{

    fun showPhotos(photos: List<Photo>)

    fun openImageFragment(path: String)

}