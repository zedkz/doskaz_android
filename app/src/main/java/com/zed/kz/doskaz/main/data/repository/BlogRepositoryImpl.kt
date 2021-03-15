package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single

class BlogRepositoryImpl(
    private val serverService: ServerService
): BlogRepository{

    override fun getBlogList(
        page: Int,
        categoryId: Int?,
        period: String?,
        search: String?
    ): Single<Pagination> =
        serverService.getBlogList(page, categoryId, period, search)

    override fun getBlog(id: Int): Single<BlogWrapper> =
        serverService.getBlog(id)

    override fun getBlogComments(
        id: Int,
        sortBy: String,
        sortOrder: String
    ): Single<BlogCommentWrapper> =
        serverService.getBlogComments(id, sortBy, sortOrder)

    override fun createBlogComment(id: Int, blogComment: BlogComment): Completable =
        serverService.createBlogComment(id, blogComment)

    override fun getBlogCategories(): Single<List<BlogCategory>> =
        serverService.getBlogCategories()

}