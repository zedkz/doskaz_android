package com.zed.kz.doskaz.global.base

import com.arellomobile.mvp.MvpView

interface BaseMvpView: MvpView{

    fun showMessage(message: String)

    fun showLongMessage(message: String)

    fun showProgressBar(show: Boolean)

}