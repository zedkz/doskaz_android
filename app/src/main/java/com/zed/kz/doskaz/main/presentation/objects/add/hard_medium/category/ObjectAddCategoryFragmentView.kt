package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.category

import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ObjectAddCategoryFragmentView : BaseMvpView{

    fun showCategoryData(dataList: List<Category>)
    fun openAddObjectCommonFragment()
    fun openAddObjectDynamicFragment(type: String, dataList: List<AddObject>)
    fun showFormTitle(title: String)
    fun closeThisFragment()

}