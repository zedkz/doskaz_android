package com.zed.kz.doskaz.main.presentation.profile.my.award

import com.zed.kz.doskaz.entity.Award
import com.zed.kz.doskaz.entity.Event
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MyAwardFragmentView : BaseMvpView{

    fun showAwards(dataList: List<Award>)

    fun showEvents(dataList: List<Event>)

}