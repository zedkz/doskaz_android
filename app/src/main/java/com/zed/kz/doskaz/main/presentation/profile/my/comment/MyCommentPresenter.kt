package com.zed.kz.doskaz.main.presentation.profile.my.comment

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Comment
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.presentation.Paginator
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.UserInteractor

@InjectViewState
class MyCommentPresenter(
    private val userInteractor: UserInteractor
): BasePresenter<MyCommentFragmentView>(){

    private var sort = AppConstants.SORT_DATE_DESC

    fun onFirstInit(){
        loadFirstPage()
    }

    val paginator = Paginator(
        { userInteractor.getMyComments(sort, it) },
        object : Paginator.ViewController<Comment>{
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState?.showProgressBar(false)
            }

            override fun showEmptyView(show: Boolean) {

            }

            override fun showData(show: Boolean, data: List<Comment>) {
                viewState?.showComments(data)
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
            1 -> sort = AppConstants.SORT_POPULARITY_DESC
        }
        loadFirstPage()
    }

}