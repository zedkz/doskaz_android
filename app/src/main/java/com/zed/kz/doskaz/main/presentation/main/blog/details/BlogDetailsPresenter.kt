package com.zed.kz.doskaz.main.presentation.main.blog.details

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.entity.BlogCommentWrapper
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.BlogInteractor
import io.reactivex.Single

@InjectViewState
class BlogDetailsPresenter(
    private val blogId: Int,
    private val blogInteractor: BlogInteractor
): BasePresenter<BlogDetailsFragmentView>(){

    private var sort = AppConstants.SORT_DESC

    fun onFirstInit(){
        getBlog(blogId)
        getBlogComments(blogId)
    }

    private fun getBlog(id: Int){
        blogInteractor.getBlog(id)
            .subscribe(
                {
                    it.post?.let { viewState?.showBlogInfo(it) }
                    it.similar?.let { viewState?.showSimilarBlog(it) }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onSimilarItemClicked(blog: Blog){
        blog.id?.let { viewState?.openBlogDetailsFragment(it) }
    }

    private fun getBlogComments(id: Int){
        blogInteractor.getBlogComments(
            id = id,
            sortOrder = sort
        ).subscribe(
                {
                    viewState?.showCommentCount(it.count ?: 0)
                    it.items?.let { viewState?.showComments(it) }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onCommentReplyBtnClicked(blogComment: BlogComment){
        blogComment.id?.let { viewState?.openCreateObjectReviewFragment(blogId, it) }
    }

    fun refreshBlogComments(){
        getBlogComments(blogId)
    }

    fun onSortItemSelected(position: Int){
        when(position){
            0 -> sort = AppConstants.SORT_DESC
            1 -> sort = AppConstants.SORT_ASC
        }
        getBlogComments(blogId)
    }

    fun onCreateCommentBtnClicked(){
        if (LocalStorage.getAccessToken() != LocalStorage.PREF_NO_VAL)
            viewState?.openCreateObjectReviewFragment(blogId, null)
        else
            viewState?.openSignInFragment()
    }
}