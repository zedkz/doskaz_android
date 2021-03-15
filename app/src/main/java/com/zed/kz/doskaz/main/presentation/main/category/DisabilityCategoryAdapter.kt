package com.zed.kz.doskaz.main.presentation.main.category

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.DisabilityCategory
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import kotlinx.android.synthetic.main.item_disability_category.view.*

class DisabilityCategoryAdapter(val onItemSelectedListener: (DisabilityCategory) -> Unit): RecyclerView.Adapter<DisabilityCategoryAdapter.PhotoHolder>(){

    private var dataList: List<DisabilityCategory> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_disability_category, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<DisabilityCategory>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(disabilityCategory: DisabilityCategory){
            itemView.apply {
                if (disabilityCategory.isSelected)
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlueLightTrans))
                else
                    setBackgroundColor(Color.WHITE)

                imgIconDCItem.setImageResource(context.resources.getIdentifier(disabilityCategory.icon, "drawable", context.packageName))
                txtTitleDCItem.text = if (LocalStorage.getLang() == AppConstants.LANG_RU) disabilityCategory.title else disabilityCategory.kazTitle
                setOnClickListener { onItemSelectedListener.invoke(disabilityCategory) }
            }
        }
    }

}