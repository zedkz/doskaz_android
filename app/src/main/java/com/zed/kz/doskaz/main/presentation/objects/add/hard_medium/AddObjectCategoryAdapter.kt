package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.item_add_object_category.view.*

class AddObjectCategoryAdapter(val onItemSelectedListener: (Int) -> Unit): RecyclerView.Adapter<AddObjectCategoryAdapter.MyViewHolder>(){

    private var dataList: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_object_category, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun submitData(dataList: List<Category>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(category: Category, position: Int){
            itemView.apply {
                txtTitleAOCI?.text = category.title
                imgReadyAOCI?.visibility = if (category.isSelected) View.VISIBLE else View.INVISIBLE
                setOnClickListener { onItemSelectedListener.invoke(position) }
            }
        }

    }

}