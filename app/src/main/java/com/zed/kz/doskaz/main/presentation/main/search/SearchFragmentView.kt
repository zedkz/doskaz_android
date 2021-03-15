package com.zed.kz.doskaz.main.presentation.main.search

import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.BaseMvpView

interface SearchFragmentView : BaseMvpView{

    fun showObjectItemsData(dataList: List<ObjectItem>)

    fun openMainObjectBottomSheetFragment(objectId: Int)

    fun closeThisFragmentWithResult(objectItem: ObjectItem)

}