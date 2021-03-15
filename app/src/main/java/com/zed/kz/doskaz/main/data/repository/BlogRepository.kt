package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.*
import io.reactivex.Completable
import io.reactivex.Single

interface BlogRepository {

    fun getBlogList(
        page: Int,
        categoryId: Int?,
        period: String?,
        search: String?
    ): Single<Pagination>

    fun getBlog(id: Int): Single<BlogWrapper>

    fun getBlogComments(
        id: Int,
        sortBy: String,
        sortOrder: String
    ): Single<BlogCommentWrapper>

    fun createBlogComment(id: Int, blogComment: BlogComment): Completable

    fun getBlogCategories(): Single<List<BlogCategory>>

}