package com.zed.kz.doskaz.main.presentation.main.objects.history

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class HistoryObjectPresenter : BasePresenter<HistoryObjectFragmentView>(){

    fun onFirstInit(){
        LocalStorage.getCurrentObjectItem().history?.let { viewState?.showDataList(it) }
    }

}