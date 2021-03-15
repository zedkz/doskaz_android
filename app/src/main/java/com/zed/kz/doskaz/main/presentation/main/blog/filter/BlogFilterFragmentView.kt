package com.zed.kz.doskaz.main.presentation.main.blog.filter

import com.zed.kz.doskaz.entity.BlogCategory
import com.zed.kz.doskaz.global.base.BaseMvpView

interface BlogFilterFragmentView : BaseMvpView{

    fun showFilterData(dataList: List<BlogCategory>)

    fun closeThisFragmentWithResult(categoryId: Int)

}