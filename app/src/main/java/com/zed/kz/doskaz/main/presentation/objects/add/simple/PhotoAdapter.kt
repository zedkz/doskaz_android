package com.zed.kz.doskaz.main.presentation.objects.add.simple

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(val onAddItemClicked: () -> Unit, val onRemoveItemClicked: (Int) -> Unit): RecyclerView.Adapter<PhotoAdapter.PhotoHolder>(){

    private var dataList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: MutableList<String>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(path: String){
            itemView.apply {
                if (path.isEmpty()){
                    imgPhotoObject.setImageResource(R.drawable.ic_65)
                }else{
                    imgPhotoObject.setImageURI(Uri.parse(path))
                }

                imgPhotoObject.setOnClickListener { if (path.isEmpty()) onAddItemClicked.invoke() }
            }
        }

    }

}