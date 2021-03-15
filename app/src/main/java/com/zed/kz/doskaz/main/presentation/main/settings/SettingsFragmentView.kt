package com.zed.kz.doskaz.main.presentation.main.settings

import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.Settings
import com.zed.kz.doskaz.global.base.BaseMvpView

interface SettingsFragmentView : BaseMvpView{

    fun showData(dataList: List<Settings>)

    fun openDisabilityCategoryFragment()

    fun openListDialogFragment(dataList: List<ListItem>)

    fun openLangDialogFragment(dataList: ArrayList<String>)

    fun changeLanguage()

}