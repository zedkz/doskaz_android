package com.zed.kz.doskaz.main.presentation.profile.my.complaint

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Complaint
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.presentation.Paginator
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class MyComplaintPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<MyComplaintFragmentView>(){

    private var sort = AppConstants.SORT_DATE_DESC

    fun onFirstInit(){
        loadFirstPage()
    }

    val paginator = Paginator(
        { userInteractor.getMyComplaints(sort, it) },
        object : Paginator.ViewController<Complaint>{
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState?.showProgressBar(false)
            }

            override fun showEmptyView(show: Boolean) {

            }

            override fun showData(show: Boolean, data: List<Complaint>) {
                viewState?.showComplaints(data)
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
            0 -> sort = AppConstants.SORT_DATE_DESC
            1 -> sort = AppConstants.SORT_DATE_ASC
        }
        loadFirstPage()
    }

}