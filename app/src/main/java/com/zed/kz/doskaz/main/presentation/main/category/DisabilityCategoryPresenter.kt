package com.zed.kz.doskaz.main.presentation.main.category

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.DisabilityCategory
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor

@InjectViewState
class DisabilityCategoryPresenter(
    private val isFromSettings: Boolean,
    private val listInteractor: ListInteractor
): BasePresenter<DisabilityCategoryFragmentView>(){

    fun onFirstInit(){
        getDisabilityCategories()
    }

    private fun getDisabilityCategories(){
        listInteractor.getDisabilityCategories()
            .subscribe(
                {
                    it.forEach {
                        if (it.key == LocalStorage.getCurrentDisabilityCategory()?.key)
                            it.isSelected = true
                    }
                    viewState?.showData(it)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onDisabilityCategorySelected(disabilityCategory: DisabilityCategory){
        LocalStorage.setCurrentDisabilityCategory(disabilityCategory)
        if (isFromSettings){
            viewState?.closeThisFragment()
        }else{
            viewState?.openHomeFragment()
        }
    }

}