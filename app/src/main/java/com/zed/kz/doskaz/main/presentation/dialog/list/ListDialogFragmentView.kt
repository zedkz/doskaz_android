package com.zed.kz.doskaz.main.presentation.dialog.list

import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ListDialogFragmentView : BaseMvpView{

    fun showData(dataList: List<ListItem>)
    fun closeThisFragmentWithResult(listItem: ListItem)

}