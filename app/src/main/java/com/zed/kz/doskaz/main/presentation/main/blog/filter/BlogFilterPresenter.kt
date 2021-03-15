package com.zed.kz.doskaz.main.presentation.main.blog.filter

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.BlogCategory
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.main.data.interactor.BlogInteractor

@InjectViewState
class BlogFilterPresenter(
    private val blogInteractor: BlogInteractor
): BasePresenter<BlogFilterFragmentView>(){

    private var blogCategories: List<BlogCategory> = listOf()
    private var selectedCategoryId: Int? = null

    fun onFirstInit(){
        getBlogCategories()
    }

    private fun getBlogCategories(){
        blogInteractor.getBlogCategories()
            .subscribe(
                {
                    blogCategories = it
                    viewState?.showFilterData(blogCategories)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onItemSelected(blogCategory: BlogCategory){
        blogCategories.forEach { it.isSelected = false }
        blogCategory.isSelected = true
        selectedCategoryId = blogCategory.id
        viewState?.showFilterData(blogCategories)
    }

    fun onReadyBtnClicked(){
        selectedCategoryId?.let { viewState?.closeThisFragmentWithResult(it) }
    }

}