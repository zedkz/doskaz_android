package com.zed.kz.doskaz.main.presentation.main.objects.info.details

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.global.presentation.BasePresenter

@InjectViewState
class ObjectInfoDetailsPresenter(
    private val dataList: List<AddObject>
): BasePresenter<ObjectInfoDetailsFragmentView>(){

    fun onFirstInit(){
        viewState?.showDataList(dataList)
    }

}