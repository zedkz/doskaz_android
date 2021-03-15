package com.zed.kz.doskaz.main.presentation.main.home

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor

@InjectViewState
class HomePresenter(
    private val listInteractor: ListInteractor
): BasePresenter<HomeFragmentView>(){

    fun onFirstInit(){
        viewState?.openMapFragment()
        if (LocalStorage.getCurrentCity() == null)
            detectCity()
        else
            viewState?.showCurrentCity(LocalStorage.getCurrentCity()?.name ?: "")
    }

    private fun detectCity(){
        listInteractor.detectCity()
            .subscribe(
                {
                    viewState?.showCurrentCity(it.name ?: "")
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onProfileMenuItemClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openProfileFragment()
        else
            viewState?.openSignInFragment()
    }

    fun onEventDrawerItemClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openShowProfileFragment(AppConstants.PROFILE_TYPE_EVENT)
        else
            viewState?.openSignInFragment()
    }

    fun onTaskDrawerItemClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openShowProfileFragment(AppConstants.PROFILE_TYPE_TASK)
        else
            viewState?.openSignInFragment()
    }

    fun onCommentDrawerItemClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openShowProfileFragment(AppConstants.PROFILE_TYPE_COMMENT)
        else
            viewState?.openSignInFragment()
    }

    fun onObjectDrawerItemClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openShowProfileFragment(AppConstants.PROFILE_TYPE_OBJECT)
        else
            viewState?.openSignInFragment()
    }

}