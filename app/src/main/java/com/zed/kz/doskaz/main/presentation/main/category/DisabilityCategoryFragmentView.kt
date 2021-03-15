package com.zed.kz.doskaz.main.presentation.main.category

import com.zed.kz.doskaz.entity.DisabilityCategory
import com.zed.kz.doskaz.global.base.BaseMvpView

interface DisabilityCategoryFragmentView : BaseMvpView{

    fun showData(dataList: List<DisabilityCategory>)

    fun openHomeFragment()

    fun closeThisFragment()

}