package com.zed.kz.doskaz.main.presentation.main.blog.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.BlogCategory
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.setCircleImageUrl
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_blog.view.*
import kotlinx.android.synthetic.main.item_blog_comment.view.*
import kotlinx.android.synthetic.main.item_blog_filter.view.*
import kotlinx.android.synthetic.main.item_blog_similar.view.*

class BlogCategoryAdapter(val onItemSelectedListener: (BlogCategory) -> Unit): RecyclerView.Adapter<BlogCategoryAdapter.MyTaskViewHolder>(){

    private var dataList: List<BlogCategory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_blog_filter, parent, false), onItemSelectedListener)

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<BlogCategory>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyTaskViewHolder(view: View, val onItemSelectedListener: (BlogCategory) -> Unit): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(blogCategory: BlogCategory){
            itemView.apply {
                checkboxBlogFilter?.text = blogCategory.title
                if(blogCategory.isSelected){
                    checkboxBlogFilter?.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_86), null)
                }else {
                    checkboxBlogFilter?.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_85), null)
                }
                setOnClickListener { onItemSelectedListener.invoke(blogCategory) }
            }
        }
    }

}