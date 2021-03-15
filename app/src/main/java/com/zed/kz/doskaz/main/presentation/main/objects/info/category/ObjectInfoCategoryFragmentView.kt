package com.zed.kz.doskaz.main.presentation.main.objects.info.category

import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ObjectInfoCategoryFragmentView : BaseMvpView{

    fun showCategoryData(dataList: List<Category>)
    fun openInfoDetailsFragment(dataList: List<AddObject>)

}