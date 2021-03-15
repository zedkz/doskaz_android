package com.zed.kz.doskaz.main.presentation.profile.my.task

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.presentation.Paginator
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class MyTaskPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<MyTaskFragmentView>(){

    private var sort = AppConstants.SORT_CREATED_AT_DESC

    val SORT_POSITION_NEW = 0
    val SORT_POSITION_OLD = 1

    fun onFirstInit(){
        loadFirstPage()
    }

    val paginator = Paginator(
        { userInteractor.getMyTasks(sort, it) },
        object : Paginator.ViewController<Task>{
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState?.showProgressBar(false)
            }

            override fun showEmptyView(show: Boolean) {

            }

            override fun showData(show: Boolean, data: List<Task>) {
                viewState?.showTasks(data)
            }

            override fun showErrorMessage(error: Throwable) {

            }

            override fun showRefreshProgress(show: Boolean) {

            }

            override fun showPageProgress(show: Boolean) {

            }
        }
    )

    fun loadFirstPage() = paginator.refresh()

    fun loadNextPage() = paginator.loadNewPage()

    fun onSortItemSelected(position: Int){
        when(position){
            SORT_POSITION_NEW -> sort = AppConstants.SORT_CREATED_AT_DESC
            SORT_POSITION_OLD -> sort = AppConstants.SORT_CREATED_AT_ASC
        }
        loadFirstPage()
    }

}