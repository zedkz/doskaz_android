package com.zed.kz.doskaz.main.presentation.main.filter

import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseMvpView

interface FilterFragmentView : BaseMvpView{

    fun showCategoriesData(dataList: List<Category>)

    fun closeThisFragmentWithResult(params: Map<String, String>?)

}