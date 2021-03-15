package com.zed.kz.doskaz.main.presentation.main.search

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor

@InjectViewState
class SearchPresenter(
    private val cityId: Int,
    private val objectInteractor: ObjectInteractor
): BasePresenter<SearchFragmentView>(){

    private var filteredData : List<ObjectItem> = listOf()

    fun filterObjects(query: String){
        LocalStorage.setCurrentQuery(query)
        if (query.isEmpty()){
            viewState?.showObjectItemsData(listOf())
            return
        }

        viewState?.showProgressBar(true)
        objectInteractor.searchObjects(cityId = cityId, query = query)
            .subscribe(
                {
                    filteredData = it
                    viewState?.showObjectItemsData(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onSearchItemSelected(objectItem: ObjectItem){
        viewState?.closeThisFragmentWithResult(objectItem)
    }

    fun showOnMapBtnClicked(query: String){
        //viewState?.closeThisFragmentWithResult(query)
    }


}