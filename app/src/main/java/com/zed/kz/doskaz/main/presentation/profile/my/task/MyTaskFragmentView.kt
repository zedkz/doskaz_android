package com.zed.kz.doskaz.main.presentation.profile.my.task

import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MyTaskFragmentView : BaseMvpView{

    fun showTasks(dataList: List<Task>)

}