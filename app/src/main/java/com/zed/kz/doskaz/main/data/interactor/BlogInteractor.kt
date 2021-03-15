package com.zed.kz.doskaz.main.data.interactor

import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.GsonHelper
import com.zed.kz.doskaz.main.data.repository.BlogRepository
import io.reactivex.Completable
import io.reactivex.Single

class BlogInteractor(
    private val schedulersProvider: SchedulersProvider,
    private val blogRepository: BlogRepository
){

    fun getBlogList(
        page: Int,
        categoryId: Int? = null,
        period: String? = null,
        search: String? = null
    ): Single<List<Blog>> =
        blogRepository.getBlogList(page, categoryId, period, search)
            .map {
                val blogList = GsonHelper.getModelArray(it.items, Blog::class.java)
                blogList
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getBlog(id: Int): Single<BlogWrapper> =
        blogRepository.getBlog(id)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getBlogComments(
        id: Int,
        sortBy: String = AppConstants.SORT_CREATED_AT,
        sortOrder: String = AppConstants.SORT_DESC
    ): Single<BlogCommentWrapper> =
        blogRepository.getBlogComments(id, sortBy, sortOrder)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun createBlogComment(id: Int, blogComment: BlogComment): Completable =
        blogRepository.createBlogComment(id, blogComment)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getBlogCategories(): Single<List<BlogCategory>> =
        blogRepository.getBlogCategories()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}