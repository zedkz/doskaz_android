package com.zed.kz.doskaz.main.presentation.main.objects.review.main

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class ReviewObjectPresenter : BasePresenter<ReviewObjectFragmentView>(){

    fun onFirstInit(){
        LocalStorage.getCurrentObjectItem().reviews?.let { viewState?.showDataList(it) }
    }

}