package com.zed.kz.doskaz.main.presentation.objects.add.simple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.views.ObjectEditTextView
import kotlinx.android.synthetic.main.item_video_object.view.*

class VideoAdapter(val onAddItemClicked: () -> Unit, val onRemoveItemClicked: (Int) -> Unit): RecyclerView.Adapter<VideoAdapter.VideoHolder>(){

    private var dataList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder =
        VideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video_object, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun submitData(dataList: MutableList<String>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class VideoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(link: String, position: Int){
            itemView.apply {
                if ((position == 0 && dataList.size == 1) || position == dataList.size - 1) {
                    oetVideoObjectItem.setQuestionIcon(R.drawable.ic_70)
                } else {
                    oetVideoObjectItem.setQuestionIcon(R.drawable.ic_69)
                }

                oetVideoObjectItem.setOnObjectEditTextListeners(
                    object : ObjectEditTextView.ObjectEditTextListeners{
                        override fun onQuestionIconClicked() {
                            if ((position == 0 && dataList.size == 1) || position == dataList.size - 1) {
                                onAddItemClicked.invoke()
                            } else {
                                onRemoveItemClicked.invoke(position)
                            }
                        }

                        override fun onValueChanged(value: String) {
                            dataList[position] = value
                        }
                    }
                )

                oetVideoObjectItem.setValue(link)
            }

        }

    }

}