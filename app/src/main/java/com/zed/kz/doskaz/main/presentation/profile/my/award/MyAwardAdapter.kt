package com.zed.kz.doskaz.main.presentation.profile.my.award

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Award
import kotlinx.android.synthetic.main.item_my_award.view.*

class MyAwardAdapter(): RecyclerView.Adapter<MyAwardAdapter.MyTaskViewHolder>(){

    private var dataList: List<Award> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_award, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Award>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(award: Award){
            itemView.apply {
                txtTitleMyAward.text = award.title
            }
        }
    }

}