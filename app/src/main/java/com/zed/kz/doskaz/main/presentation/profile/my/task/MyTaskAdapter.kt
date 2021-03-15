package com.zed.kz.doskaz.main.presentation.profile.my.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.extension.getFormattedDate
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_my_task.view.*

class MyTaskAdapter(val onBottomReachedListener: () -> Unit): RecyclerView.Adapter<MyTaskAdapter.MyTaskViewHolder>(){

    private var dataList: List<Task> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_task, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_SIZE) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Task>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(task: Task){
            itemView.apply {
                txtNameMyTask.text = task.title
                txtDateMyTask.text = task.createdAt?.getFormattedDateT()
                txtPointsMyTask.text = "${task.points} ${context.getString(R.string.profile_point)}"

                if (task.completedAt == null)
                    imgMyTask.setImageResource(R.drawable.ic_48)
                else
                    imgMyTask.setImageResource(R.drawable.ic_49)
            }
        }
    }

}