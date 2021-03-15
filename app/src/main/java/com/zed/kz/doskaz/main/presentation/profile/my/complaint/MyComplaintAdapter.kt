package com.zed.kz.doskaz.main.presentation.profile.my.complaint

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Complaint
import com.zed.kz.doskaz.global.extension.getFormattedDateAndTimeT
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.getFormattedTimeT
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_my_complaint.view.*

class MyComplaintAdapter(val onBottomReachedListener: () -> Unit): RecyclerView.Adapter<MyComplaintAdapter.MyTaskViewHolder>(){

    private var dataList: List<Complaint> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTaskViewHolder =
        MyTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_my_complaint, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyTaskViewHolder, position: Int) {
        if (position == dataList.size - AppConstants.PAGE_SIZE) onBottomReachedListener.invoke()
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Complaint>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    class MyTaskViewHolder(view: View): RecyclerView.ViewHolder(view){

        @SuppressLint("SetTextI18n")
        fun bind(complaint: Complaint){
            itemView.apply {
                txtTitleMyComplaints.text = complaint.title
                txtDateMyComplaints.text = String.format(
                    context.getString(R.string.profile_date_text),
                    complaint.date?.getFormattedDateT(),
                    complaint.date?.getFormattedTimeT()
                )
                txtTextMyComplaints.text = if (complaint.type == AppConstants.COMPLAINT_TYPE_1)
                    context.getString(R.string.complaint_type_1)
                else
                    context.getString(R.string.complaint_type_2)

                setOnClickListener {
                    LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(
                            Intent(AppConstants.FILTER_DOWNLOAD_FILE).apply {
                                putExtra(
                                    AppConstants.BUNDLE_DOC_ID,
                                    complaint.id
                                )
                                putExtra(
                                    AppConstants.BUNDLE_JUST_DOWNLOAD,
                                    true
                                )
                            }
                        )
                }
            }
        }
    }

}