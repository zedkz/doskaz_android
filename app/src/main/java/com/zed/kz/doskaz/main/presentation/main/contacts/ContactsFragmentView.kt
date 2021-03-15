package com.zed.kz.doskaz.main.presentation.main.contacts

import com.zed.kz.doskaz.entity.Regional
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ContactsFragmentView : BaseMvpView{

    fun clearContactsViews()
    fun showRegional(dataList: List<Regional>)

}