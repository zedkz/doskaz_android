package com.zed.kz.doskaz.main.presentation.dialog.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val onItemSelectedListener: (ListItem) -> Unit): RecyclerView.Adapter<ListAdapter.PhotoHolder>(){

    private var dataList: List<ListItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<ListItem>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(listItem: ListItem){
            itemView.apply {
                txtTitleListItem.text = listItem.title
                if (listItem.selected) imgReadyListItem.visibility = View.VISIBLE else imgReadyListItem.visibility = View.INVISIBLE

                setOnClickListener {
//                    dataList.forEach { it.selected = false }
//                    listItem.selected = !listItem.selected
                    listItem.selected = true
                    onItemSelectedListener.invoke(listItem)
                }
            }
        }

    }

}