package com.zed.kz.doskaz.main.presentation.activity

import com.zed.kz.doskaz.global.base.BaseMvpView

interface MainActivityView : BaseMvpView{

    fun openChooseLanguageFragment()

    fun openWelcomeFragment()

    fun openSignInFragment()

    fun openHomeFragment()

    fun openDisabilityCategoryFragment()

    fun showDownloadAlertDialog(docId: Int)

    fun showObjectCreatedAlertDialog()
}