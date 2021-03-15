package com.zed.kz.doskaz.main.presentation.profile.my.objects

import com.zed.kz.doskaz.entity.MyObject
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MyObjectsFragmentView : BaseMvpView{

    fun showObjects(dataList: List<MyObject>)

}