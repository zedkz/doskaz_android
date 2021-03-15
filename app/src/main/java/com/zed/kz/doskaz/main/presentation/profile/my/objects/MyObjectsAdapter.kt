package com.zed.kz.doskaz.main.presentation.profile.my.objects

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.MyObject
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_my_objects.view.*

class MyObjectsAdapter(val onBottomReachedListener: () -> Unit): RecyclerView.Adapter<MyObjectsAdapter.MyTaskViewHolder>(){

    private var dataList: List<MyObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_objects, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_SIZE) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<MyObject>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(myObject: MyObject){
            itemView.apply {

                imgMyObjects.setImageUrl(myObject.image)

                if(myObject.overallScore == AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE ||
                        myObject.overallScore == AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE)
                    imgStatusMyObjects.setImageResource(R.drawable.ic_63)
                else if (myObject.overallScore == AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE ||
                        myObject.overallScore == AppConstants.OVERALL_SCOPE_NOT_PROVIDED)
                    imgStatusMyObjects.setImageResource(R.drawable.ic_45)
                else
                    imgStatusMyObjects.setImageResource(R.drawable.ic_46)

                txtNameMyObjects.text = myObject.title
                txtCommentsMyObjects.text = myObject.reviewsCount.toString()
                txtTicketsMyObjects.text = myObject.complaintsCount.toString()
                txtDateMyObjects.text = myObject.date?.getFormattedDateT()
            }
        }
    }

}