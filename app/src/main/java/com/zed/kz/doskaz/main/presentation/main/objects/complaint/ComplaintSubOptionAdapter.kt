package com.zed.kz.doskaz.main.presentation.main.objects.complaint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.SubOption
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.item_complaint_sub_option.view.*

class ComplaintSubOptionAdapter(val onItemSelectedListener: (SubOption) -> Unit): RecyclerView.Adapter<ComplaintSubOptionAdapter.HistoryHolder>(){

    private var dataList: List<SubOption> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder =
        HistoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complaint_sub_option, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<SubOption>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class HistoryHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(subOption: SubOption){
            itemView.apply {

                txtTitleCSOI?.text = subOption.label
                if (subOption.isSelected) imgCSOI?.visibility = View.VISIBLE else imgCSOI?.visibility = View.INVISIBLE

                setOnClickListener {
                    subOption.isSelected = !subOption.isSelected
                    onItemSelectedListener.invoke(subOption)
                    notifyDataSetChanged()
                }
            }
        }

    }

}