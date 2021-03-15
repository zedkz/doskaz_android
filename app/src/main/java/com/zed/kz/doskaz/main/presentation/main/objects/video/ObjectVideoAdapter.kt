package com.zed.kz.doskaz.main.presentation.main.objects.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.Video
import com.zed.kz.doskaz.global.extension.setImageUrl
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.item_object_video.view.*

class ObjectVideoAdapter(): RecyclerView.Adapter<ObjectVideoAdapter.PhotoHolder>(){

    private var dataList: List<Video> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_object_video, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Video>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(video: Video){
            itemView.apply {
                videoPlayerObjectVideo?.setUp(
                    video.url,
                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,
                    ""
                )
                videoPlayerObjectVideo?.thumbImageView?.setImageUrl(video.thumbnail)
                videoPlayerObjectVideo?.fullscreenButton?.visibility = View.INVISIBLE
                videoPlayerObjectVideo?.fullscreenButton?.isEnabled = false
            }
        }

    }

}