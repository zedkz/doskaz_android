package com.zed.kz.doskaz.main.presentation.profile.my.comment

import com.zed.kz.doskaz.entity.Comment
import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MyCommentFragmentView : BaseMvpView{

    fun showComments(dataList: List<Comment>)

    fun openBlogDetailsFragment(blogId: Int)

}