package com.zed.kz.doskaz.main.presentation.main.map

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.zed.kz.doskaz.entity.Cluster
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.BaseMvpView

@StateStrategyType(OneExecutionStateStrategy::class)
interface MapFragmentView : BaseMvpView{

    fun boundsReady()
    fun showObjectsOnMap(dataList: List<ObjectItem>, clusters: List<Cluster>)
    fun openSearchFragment(cityId: Int)
    fun openFilterFragment()
    fun openAddSimpleObjectFragment()
    fun showSearchTitle(title: String)
    fun openMainObjectFragment(objectId: Int)
    fun openSignInFragment()
    fun openObjectComplaintFragment()
    fun selectMarker(objectItem: ObjectItem)

}