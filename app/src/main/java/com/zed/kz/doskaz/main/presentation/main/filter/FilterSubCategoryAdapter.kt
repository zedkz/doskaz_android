package com.zed.kz.doskaz.main.presentation.main.filter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Category
import kotlinx.android.synthetic.main.item_filter_sub_category.view.*
import timber.log.Timber

class FilterSubCategoryAdapter(val onItemSelectedListener: (Category) -> Unit): RecyclerView.Adapter<FilterSubCategoryAdapter.PhotoHolder>(){

    private var dataList: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_sub_category, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Category>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(category: Category){
            itemView.apply {
                checkboxSubCategoryFilter.text = category.title
                checkboxSubCategoryFilter.isChecked = category.isSelected
                checkboxSubCategoryFilter.setOnCheckedChangeListener { _, isChecked ->
                    category.isSelected = isChecked
                    onItemSelectedListener.invoke(category)
                }
            }
        }

    }

}