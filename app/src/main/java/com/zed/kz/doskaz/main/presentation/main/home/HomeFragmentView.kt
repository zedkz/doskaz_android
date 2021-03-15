package com.zed.kz.doskaz.main.presentation.main.home

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zed.kz.doskaz.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface HomeFragmentView : BaseMvpView{

    fun openMapFragment()
    fun openProfileFragment()
    fun openAboutFragment()
    fun openInstructionFragment()
    fun openContactsFragment()
    fun openBlogListFragment()
    fun openShowProfileFragment(type: String)
    fun showCurrentCity(name: String)
    fun openSignInFragment()
    fun openSettingsFragment()

}