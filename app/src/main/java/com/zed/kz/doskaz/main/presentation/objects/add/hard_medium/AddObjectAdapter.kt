package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import kotlinx.android.synthetic.main.item_add_object_content.view.*
import kotlinx.android.synthetic.main.item_add_object_header.view.*
import kotlinx.android.synthetic.main.item_add_object_header_small.view.*

class AddObjectAdapter(val onContentItemSelectedListener: (AddObject) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var dataList: List<AddObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            AddObject.TYPE_HEADER -> HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_object_header, parent, false))
            AddObject.TYPE_HEADER_SMALL -> HeaderSmallViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_object_header_small, parent, false))
            else -> ContentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_add_object_content, parent, false))
        }


    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int = dataList[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HeaderViewHolder -> holder.bind(dataList[position])
            is HeaderSmallViewHolder -> holder.bind(dataList[position])
            is ContentViewHolder -> holder.bind(dataList[position])
        }
    }

    fun submitData(dataList: List<AddObject>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(addObject: AddObject){
            itemView.apply {
                txtTitleAddObjectHeader?.text = addObject.title
            }
        }

    }

    inner class HeaderSmallViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(addObject: AddObject){
            itemView.apply {
                txtTitleAddObjectHeaderSmall?.text = addObject.title
            }
        }

    }

    inner class ContentViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(addObject: AddObject){
            itemView.apply {
                otvAddObjectContent?.setTitle(addObject.title)
                otvAddObjectContent?.setValue(addObject.value)
                setOnClickListener { onContentItemSelectedListener.invoke(addObject) }
            }
        }

    }

}