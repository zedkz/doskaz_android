package com.zed.kz.doskaz.main.presentation.profile.my.objects

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.MyObject
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.presentation.Paginator
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class MyObjectsPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<MyObjectsFragmentView>(){

    private var sort = AppConstants.SORT_DATE_DESC
    private var overallScope = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE

    val SORT_POSITION_NEW = 0
    val SORT_POSITION_OLD = 1

    fun onFirstInit(){
        loadFirstPage()
    }

    val paginator = Paginator(
        { userInteractor.getMyObjects(overallScope, sort, it) },
        object : Paginator.ViewController<MyObject>{
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState?.showProgressBar(false)
            }

            override fun showEmptyView(show: Boolean) {

            }

            override fun showData(show: Boolean, data: List<MyObject>) {
                viewState?.showObjects(data)
            }

            override fun showErrorMessage(error: Throwable) {

            }

            override fun showRefreshProgress(show: Boolean) {

            }

            override fun showPageProgress(show: Boolean) {

            }
        }
    )

    private fun loadFirstPage() = paginator.refresh()

    fun loadNextPage() = paginator.loadNewPage()

    fun onSortItemSelected(position: Int){
        when(position){
            SORT_POSITION_NEW -> sort = AppConstants.SORT_DATE_DESC
            SORT_POSITION_OLD -> sort = AppConstants.SORT_DATE_ASC
        }
        loadFirstPage()
    }

    fun onOverallItemSelected(position: Int){
        when(position){
            0 -> overallScope = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE
            1 -> overallScope = AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE
            2 -> overallScope = AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE
            3 -> overallScope = AppConstants.OVERALL_SCOPE_NOT_PROVIDED
            4 -> overallScope = AppConstants.OVERALL_SCOPE_UNKNOWN
        }
    }

}