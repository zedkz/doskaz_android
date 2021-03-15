package com.zed.kz.doskaz.main.presentation.main.objects.history

import com.zed.kz.doskaz.entity.object_info.History
import com.zed.kz.doskaz.global.base.BaseMvpView

interface HistoryObjectFragmentView : BaseMvpView{

    fun showDataList(dataList: List<History>)

}