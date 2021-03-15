package com.zed.kz.doskaz.main.presentation.auth.welcome

import com.zed.kz.doskaz.entity.WelcomeItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface WelcomeFragmentView : BaseMvpView{

    fun showViewPagerData(dataList: List<WelcomeItem>)

    fun setViewPagerPosition(position: Int)

    fun openDisabilityCategoryFragment()

}