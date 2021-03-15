package com.zed.kz.doskaz.main.presentation.profile.my.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Comment
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.getFormattedTimeT
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.AppConstants.COMMENT_TYPE_POST
import kotlinx.android.synthetic.main.item_my_comments.view.*

class MyCommentAdapter(val onBottomReachedListener: () -> Unit, val onPostItemListener: (Comment) -> Unit): RecyclerView.Adapter<MyCommentAdapter.MyTaskViewHolder>(){

    private var dataList: List<Comment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_comments, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_SIZE) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Comment>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(comment: Comment){
            itemView.apply {
                txtTitleMyComments.text = comment.text

                when(comment.type){
                    COMMENT_TYPE_POST -> {
                        txtDateMyComments.text = String.format(
                            context.getString(R.string.profile_post_date_text),
                            comment.date?.getFormattedDateT(),
                            comment.date?.getFormattedTimeT()
                        )
                        txtObjectMyComments.text = comment.title
                        itemView.setOnClickListener {
                            onPostItemListener.invoke(comment)
                        }
                    }
                    else -> {
                        txtDateMyComments.text = String.format(
                            context.getString(R.string.profile_date_text),
                            comment.date?.getFormattedDateT(),
                            comment.date?.getFormattedTimeT()
                        )
                        txtObjectMyComments.text = ""
                    }
                }
            }
        }
    }

}