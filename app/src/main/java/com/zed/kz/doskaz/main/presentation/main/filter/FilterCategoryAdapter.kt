package com.zed.kz.doskaz.main.presentation.main.filter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.item_filter_category.view.*

class FilterCategoryAdapter(val onItemSelectedListener: (Category) -> Unit): RecyclerView.Adapter<FilterCategoryAdapter.PhotoHolder>(){

    private var dataList: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter_category, parent, false))

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
                txtTitleCategoryFilter.text = category.title
                val filterSubCategoryAdapter = FilterSubCategoryAdapter{ onItemSelectedListener.invoke(it) }
                recyclerCategoryFilter.adapter = filterSubCategoryAdapter
                category.subCategories?.let { filterSubCategoryAdapter.submitData(it) }
                if (category.isExpose){
                    imgExposeCategoryFilter.setImageResource(R.drawable.ic_77)
                    rootCategoryFilter.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreyLight))
                    txtTitleCategoryFilter.typeface = Typeface.DEFAULT_BOLD
                    imgIconCategoryFilter.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack))
                    recyclerCategoryFilter.visible(true)
                }else{
                    imgExposeCategoryFilter.setImageResource(R.drawable.ic_76)
                    rootCategoryFilter.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                    imgIconCategoryFilter.setColorFilter(ContextCompat.getColor(context, R.color.colorTransparent))
                    txtTitleCategoryFilter.typeface = Typeface.DEFAULT
                    recyclerCategoryFilter.visible(false)
                }

                setOnClickListener {
                    if (!category.isExpose){
                        imgExposeCategoryFilter.setImageResource(R.drawable.ic_77)
                        rootCategoryFilter.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreyLight))
                        txtTitleCategoryFilter.typeface = Typeface.DEFAULT_BOLD
                        imgIconCategoryFilter.setColorFilter(ContextCompat.getColor(context, R.color.colorBlack))
                        category.isExpose = true
                        recyclerCategoryFilter.visible(true)
                    }else{
                        imgExposeCategoryFilter.setImageResource(R.drawable.ic_76)
                        rootCategoryFilter.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite))
                        imgIconCategoryFilter.setColorFilter(ContextCompat.getColor(context, R.color.colorTransparent))
                        txtTitleCategoryFilter.typeface = Typeface.DEFAULT
                        category.isExpose = false
                        recyclerCategoryFilter.visible(false)
                    }
                }
            }
        }
    }

}