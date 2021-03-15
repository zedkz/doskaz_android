package com.zed.kz.doskaz.main.presentation.main.objects.video

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class VideoObjectPresenter : BasePresenter<VideoObjectFragmentView>(){

    fun onFirstInit(){
        LocalStorage.getCurrentObjectItem().videos?.let { viewState?.showDataList(it) }
    }

}