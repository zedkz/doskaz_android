package com.zed.kz.doskaz.main.presentation.main.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.iconics.IconicsDrawable
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(val OnItemSelectedListener: (ObjectItem) -> Unit): RecyclerView.Adapter<SearchAdapter.PhotoHolder>(){

    private var dataList: List<ObjectItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<ObjectItem>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(objectItem: ObjectItem){
            itemView.apply {
                txtTitleSearchItem.text = objectItem.title
                txtAddressSearchItem.text = objectItem.address
                objectItem.icon?.let { imgIconSearchItem.setImageDrawable(IconicsDrawable(context,  it.replace("fa-", "faw-"))) }
                setOnClickListener { OnItemSelectedListener.invoke(objectItem) }
            }
        }

    }

}