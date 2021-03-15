package com.zed.kz.doskaz.main.presentation.main.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Settings
import kotlinx.android.synthetic.main.item_settings.view.*

class SettingsAdapter(val OnItemSelectedListener: (Int) -> Unit): RecyclerView.Adapter<SettingsAdapter.MyViewHolder>(){

    private var dataList: List<Settings> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_settings, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun submitData(dataList: List<Settings>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(settings: Settings, position: Int){
            itemView.apply {
                txtSettingsItem?.text = settings.title
                setOnClickListener { OnItemSelectedListener.invoke(position) }
            }
        }

    }

}