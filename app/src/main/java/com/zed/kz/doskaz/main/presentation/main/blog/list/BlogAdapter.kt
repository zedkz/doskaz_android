package com.zed.kz.doskaz.main.presentation.main.blog.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_blog.view.*

class BlogAdapter(val onBottomReachedListener: () -> Unit, val onItemClickListener: (Blog) -> Unit): RecyclerView.Adapter<BlogAdapter.MyTaskViewHolder>(){

    private var dataList: List<Blog> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false), onItemClickListener)

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_SIZE_BLOG) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Blog>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View, val onItemClickListener: (Blog) -> Unit): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(blog: Blog){
            itemView.apply {
                txtTitleBlogItem?.text = blog.title
                txtAnnotationBlogItem?.text = blog.annotation
                txtDateBlogItem?.text = blog.publishedAt?.getFormattedDateT()
                txtCategoryBlogItem?.text = blog.categoryTitle
                imgBlogItem?.setImageUrl(blog.previewImage, R.drawable.no_image_large)
                setOnClickListener { onItemClickListener.invoke(blog) }
            }
        }
    }

}