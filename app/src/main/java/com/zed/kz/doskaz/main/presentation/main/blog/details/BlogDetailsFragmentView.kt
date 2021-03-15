package com.zed.kz.doskaz.main.presentation.main.blog.details

import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.global.base.BaseMvpView

interface BlogDetailsFragmentView : BaseMvpView{

    fun showBlogInfo(blog: Blog)

    fun showSimilarBlog(dataList: List<Blog>)

    fun openBlogDetailsFragment(id: Int)

    fun showComments(dataList: List<BlogComment>)

    fun showCommentCount(count: Int)

    fun openCreateObjectReviewFragment(blogId: Int, parentId: String?)

    fun openSignInFragment()

}