package com.zed.kz.doskaz.main.presentation.main.contacts

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Regional
import com.zed.kz.doskaz.global.extension.setCircleImageUrl
import kotlinx.android.synthetic.main.item_regional.view.*

class RegionalAdapter(): RecyclerView.Adapter<RegionalAdapter.PhotoHolder>(){

    private var dataList: List<Regional> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder =
        PhotoHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_regional, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<Regional>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(regional: Regional){
            itemView.apply {
                txtNameRegionalItem?.text = regional.name
                txtDepartmentRegionalItem?.text = regional.department

                imgCallRegionalItem?.setOnClickListener { context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${regional.phone}"))) }
                imgMessageRegionalItem?.setOnClickListener {
                    val intent = Intent().apply {
                        putExtra(Intent.EXTRA_EMAIL, regional.email)
                        putExtra(Intent.EXTRA_SUBJECT, "Text")
                    }
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                }

                imgAvatarRegionalItem?.setCircleImageUrl(regional.image, R.drawable.ic_60)
            }
        }

    }

}