package com.zed.kz.doskaz.main.presentation.profile.main.show

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class ShowProfilePresenter(
    private val type: String?,
    private val userInteractor: UserInteractor
) : BasePresenter<ShowProfileFragmentView>(){

    fun onFirstInit(){
        getProfile()
        when(type){
            AppConstants.PROFILE_TYPE_COMMENT -> viewState?.setNavigationSelectedItemId(R.id.nav_my_comments)
            AppConstants.PROFILE_TYPE_EVENT -> viewState?.setNavigationSelectedItemId(R.id.nav_my_achievement)
            AppConstants.PROFILE_TYPE_OBJECT -> viewState?.setNavigationSelectedItemId(R.id.nav_my_object)
            AppConstants.PROFILE_TYPE_TASK -> viewState?.setNavigationSelectedItemId(R.id.nav_my_task)
            else -> viewState?.setNavigationSelectedItemId(R.id.nav_my_task)
        }
    }

    private fun getProfile(){
        viewState?.showProgressBar(true)
        userInteractor.getProfile()
            .subscribe(
                {
                    viewState?.showUserInfo(it)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    fun onLogoutBtnClicked(){
        LocalStorage.setCurrentQuery("")
        LocalStorage.setCurrentDisabilityCategory(null)
        LocalStorage.setUser(null)
        LocalStorage.setCurrentObjectItem(null)
        LocalStorage.setAccessToken(LocalStorage.PREF_NO_VAL)
        viewState?.openHomeFragment()
    }

}