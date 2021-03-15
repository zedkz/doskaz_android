package com.zed.kz.doskaz.main.presentation.main.objects.review.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.extension.getFormattedDateAndTimeT
import kotlinx.android.synthetic.main.item_object_review.view.*

class ObjectReviewAdapter(): RecyclerView.Adapter<ObjectReviewAdapter.PhotoHolder>(){

    private var dataList: List<Review> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_object_review, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Review>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(review: Review){
            itemView.apply {
                txtNameObjectReview?.text = review.author
                txtTextObjectReview?.text = review.text
                txtDateObjectReview?.text = review.createdAt?.getFormattedDateAndTimeT()
            }
        }

    }

}