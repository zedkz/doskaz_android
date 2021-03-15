package com.zed.kz.doskaz.main.presentation.main.blog.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.setCircleImageUrl
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_blog.view.*
import kotlinx.android.synthetic.main.item_blog_comment.view.*
import kotlinx.android.synthetic.main.item_blog_similar.view.*

class BlogCommentAdapter(val onReplyClickListener: (BlogComment) -> Unit): RecyclerView.Adapter<BlogCommentAdapter.MyTaskViewHolder>(){

    private var dataList: List<BlogComment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_blog_comment, parent, false), onReplyClickListener)

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<BlogComment>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View, val onReplyClickListener: (BlogComment) -> Unit): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(blogComment: BlogComment){
            itemView.apply {
                txtNameBlogComment?.text = blogComment.userName
                txtDateBlogComment?.text = blogComment.createdAt?.getFormattedDateT()
                txtTextBlogComment?.text = blogComment.text

                if (blogComment.userAvatar == AppConstants.BLOG_AVA)
                    imgAvatarBlogComment?.setImageResource(R.drawable.ic_60)
                else
                    imgAvatarBlogComment?.setCircleImageUrl(blogComment.userAvatar, R.drawable.ic_60)

                btnReplyBlogComment?.setOnClickListener { onReplyClickListener.invoke(blogComment) }

                blogComment.replies?.let {
                    val replyCommentAdapter = BlogCommentAdapter{ onReplyClickListener(it) }
                    recyclerReplyBlogComment?.adapter = replyCommentAdapter
                    replyCommentAdapter.submitData(it)
                }
            }
        }
    }

}