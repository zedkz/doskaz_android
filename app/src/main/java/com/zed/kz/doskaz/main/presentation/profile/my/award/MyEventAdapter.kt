package com.zed.kz.doskaz.main.presentation.profile.my.award

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Award
import com.zed.kz.doskaz.entity.Event
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import kotlinx.android.synthetic.main.item_my_award.view.*
import kotlinx.android.synthetic.main.item_my_event.view.*

class MyEventAdapter(): RecyclerView.Adapter<MyEventAdapter.MyTaskViewHolder>(){

    private var dataList: List<Event> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_event, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Event>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(event: Event){
            itemView.apply {
                txtDateMyEvent.text = event.date?.getFormattedDateT()
            }
        }
    }

}