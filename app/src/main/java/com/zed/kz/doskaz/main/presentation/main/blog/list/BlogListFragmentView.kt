package com.zed.kz.doskaz.main.presentation.main.blog.list

import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.global.base.BaseMvpView

interface BlogListFragmentView : BaseMvpView{

    fun showBlogData(dataList: List<Blog>)
    fun openBlogDetailsFragment(blogId: Int)

}