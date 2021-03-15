package com.zed.kz.doskaz.main.presentation.main.blog.details

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
import kotlinx.android.synthetic.main.item_blog_similar.view.*

class BlogSimilarAdapter(val onItemClickListener: (Blog) -> Unit): RecyclerView.Adapter<BlogSimilarAdapter.MyTaskViewHolder>(){

    private var dataList: List<Blog> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_blog_similar, parent, false), onItemClickListener)

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
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
                txtTitleBlogSimilar?.text = blog.title
                imgBlogSimilar?.setImageUrl(blog.image)
                setOnClickListener { onItemClickListener.invoke(blog) }
            }
        }
    }

}