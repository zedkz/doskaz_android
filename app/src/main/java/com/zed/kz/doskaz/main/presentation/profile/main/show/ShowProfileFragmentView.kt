package com.zed.kz.doskaz.main.presentation.profile.main.show

import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ShowProfileFragmentView : BaseMvpView{

    fun showUserInfo(user: User)

    fun showMyTaskFragment()

    fun showMyObjectsFragment()

    fun showMyCommentsFragment()

    fun showMyComplaintsFragment()

    fun showMyAwardFragment()

    fun setNavigationSelectedItemId(id: Int)

    fun openHomeFragment()
}