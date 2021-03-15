package com.zed.kz.doskaz.main.presentation.main.objects.review.create

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.BlogInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor

@InjectViewState
class CreateObjectReviewPresenter(
    private val blogId: Int,
    private val parentId: String?,
    private val resourceManager: ResourceManager,
    private val objectInteractor: ObjectInteractor,
    private val blogInteractor: BlogInteractor
): BasePresenter<CreateObjectReviewFragmentView>(){

    fun onCreateBtnClicked(text: String){
        if (blogId == -1){
            createObjectReview(text)
        }else{
            createBlogComment(blogId, parentId, text)
        }
    }

    private fun createObjectReview(text: String){
        if (text.isEmpty() || text.length < 20){
            viewState?.showMessage(resourceManager.getString(R.string.om_review_warning))
            return
        }

        viewState?.showProgressBar(true)

        objectInteractor.createObjectReview(LocalStorage.getCurrentObjectItem().id ?: 0,
            Review(text = text)
        )
            .subscribe(
                {
                    viewState?.closeThisFragment()
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    private fun createBlogComment(blogId: Int, parentId: String?, text: String){
        blogInteractor.createBlogComment(
            blogId,
            BlogComment(
                text = text,
                parentId = parentId
            )
        ).subscribe(
            {
                viewState?.closeThisFragment()
            },
            {
                it.printStackTrace()
                viewState?.showMessage(it.getObjectErrorMessage())
            }
        ).connect()
    }

}