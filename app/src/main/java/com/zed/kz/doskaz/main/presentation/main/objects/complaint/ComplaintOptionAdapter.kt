package com.zed.kz.doskaz.main.presentation.main.objects.complaint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Option
import com.zed.kz.doskaz.entity.SubOption
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.item_complaint_option.view.*
import kotlinx.android.synthetic.main.item_complaint_sub_option.view.*

class ComplaintOptionAdapter(val onItemSelectedListener: (Option) -> Unit): RecyclerView.Adapter<ComplaintOptionAdapter.HistoryHolder>(){

    private var dataList: List<Option> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder =
        HistoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_complaint_option, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Option>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class HistoryHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(option: Option){
            itemView.apply {

                val adapter = ComplaintSubOptionAdapter{
                    option.count = option.options?.filter { it.isSelected }?.size
                    if (option.count == 0) option.count = null
                    txtCountCOI?.text = option.count?.toString()
                }

                recyclerSubCOI.adapter = adapter
                option.options?.let { adapter.submitData(it) }

                txtCountCOI?.text = option.count?.toString()
                txtTitleCOI?.text = option.title

                recyclerSubCOI.visible(option.isSelected)
                if (option.isSelected){
                    imgCOI.setImageResource(R.drawable.ic_77)
                }else{
                    imgCOI.setImageResource(R.drawable.ic_76)
                }

                setOnClickListener {
                    option.isSelected = !option.isSelected
                    onItemSelectedListener.invoke(option)
                    notifyDataSetChanged()
                }
            }
        }

    }

}