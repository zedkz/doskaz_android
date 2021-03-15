package com.zed.kz.doskaz.main.presentation.main.objects.info.details

import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ObjectInfoDetailsFragmentView : BaseMvpView{

    fun showDataList(dataList: List<AddObject>)

}