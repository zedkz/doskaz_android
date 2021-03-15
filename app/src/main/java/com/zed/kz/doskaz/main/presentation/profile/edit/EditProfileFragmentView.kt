package com.zed.kz.doskaz.main.presentation.profile.edit

import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.base.BaseMvpView

interface EditProfileFragmentView : BaseMvpView{

    fun showUserInfo(user: User)

}