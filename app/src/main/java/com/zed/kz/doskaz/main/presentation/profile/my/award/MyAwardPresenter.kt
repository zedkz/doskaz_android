package com.zed.kz.doskaz.main.presentation.profile.my.award

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class MyAwardPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<MyAwardFragmentView>(){


    fun onFirstInit(){
        getMyAwards()
        getMyEvents()
    }

    private fun getMyAwards(){
        userInteractor.getMyAwards()
            .subscribe(
                {
                    viewState?.showAwards(it)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun getMyEvents(){
        userInteractor.getMyEvents()
            .subscribe(
                {
                    viewState?.showEvents(it)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

}