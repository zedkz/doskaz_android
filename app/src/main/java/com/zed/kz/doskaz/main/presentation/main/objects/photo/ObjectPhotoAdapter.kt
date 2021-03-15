package com.zed.kz.doskaz.main.presentation.main.objects.photo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Photo
import com.zed.kz.doskaz.global.extension.setImageUrl
import kotlinx.android.synthetic.main.item_photo.view.*

class ObjectPhotoAdapter(val onItemClickedListener: (Photo) -> Unit): RecyclerView.Adapter<ObjectPhotoAdapter.PhotoHolder>(){

    private var dataList: List<Photo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo_object, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Photo>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(photo: Photo){
            itemView.apply {
                imgPhotoObject.setImageUrl(photo.previewUrl)
                imgPhotoObject.setOnClickListener { onItemClickedListener.invoke(photo) }
            }
        }

    }

}