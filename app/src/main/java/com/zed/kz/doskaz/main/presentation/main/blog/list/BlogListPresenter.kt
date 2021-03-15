package com.zed.kz.doskaz.main.presentation.main.blog.list

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.Comment
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.presentation.Paginator
import com.zed.kz.doskaz.main.data.interactor.BlogInteractor
import timber.log.Timber
import java.util.*

@InjectViewState
class BlogListPresenter(
    private val blogInteractor: BlogInteractor
): BasePresenter<BlogListFragmentView>(){

    private var searchQuery: String? = null
    private var categoryId: Int? = null
    private var timer = Timer()

    fun onFirstInit(){
        viewState?.showProgressBar(true)
        loadFirstPage()
    }

    private val paginator = Paginator(
        {
            blogInteractor.getBlogList(
                page = it,
                search = searchQuery,
                categoryId = categoryId
            )
        },
        object : Paginator.ViewController<Blog>{
            override fun showEmptyProgress(show: Boolean) {

            }

            override fun showEmptyError(show: Boolean, error: Throwable?) {
                viewState?.showProgressBar(false)
            }

            override fun showEmptyView(show: Boolean) {

            }

            override fun showData(show: Boolean, data: List<Blog>) {
                viewState?.showBlogData(data)
                viewState?.showProgressBar(false)
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

    fun onSearch(query: String){
        viewState?.showProgressBar(true)
        viewState?.showBlogData(listOf())
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    searchQuery = if (query.isEmpty()) null else query
                    loadFirstPage()
                }
            },
            500
        )
    }

    fun onBlogItemClicked(blog: Blog){
        blog.id?.let { viewState?.openBlogDetailsFragment(it) }
    }

    fun onFilterResult(categoryId: Int){
        this.categoryId = categoryId
        loadFirstPage()
    }

}