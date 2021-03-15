package com.zed.kz.doskaz.main.presentation.dialog.list

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.presentation.BasePresenter
import timber.log.Timber

@InjectViewState
class ListDialogPresenter(
    private val dataList: List<ListItem>
): BasePresenter<ListDialogFragmentView>(){

    fun onFirstInit(){
        viewState?.showData(dataList)
    }

    fun onReadyBtnClicked(){
        dataList.forEach {
            if (it.selected){
                viewState?.closeThisFragmentWithResult(it)
                return@forEach
            }
        }
    }

}