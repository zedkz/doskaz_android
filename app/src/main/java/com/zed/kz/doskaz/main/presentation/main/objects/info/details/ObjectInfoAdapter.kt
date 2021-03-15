package com.zed.kz.doskaz.main.presentation.main.objects.info.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_object_info.view.*

class ObjectInfoAdapter : RecyclerView.Adapter<ObjectInfoAdapter.MyViewHolder>(){

    private var dataList: List<AddObject> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_object_info, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<AddObject>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(addObject: AddObject){
            itemView.apply {
                txtTitleObjectInfo?.text = addObject.title
                when(addObject.value){
                    AppConstants.OBJECT_ATTR_YES -> {
                        txtValueObjectInfo?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_101, 0, 0, 0)
                        txtValueObjectInfo?.text = context.getString(R.string.yes)
                    }
                    AppConstants.OBJECT_ATTR_NO -> {
                        txtValueObjectInfo?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_102, 0, 0, 0)
                        txtValueObjectInfo?.text = context.getString(R.string.no)
                    }
                    AppConstants.OBJECT_ATTR_NOT_PROVIDED -> {
                        txtValueObjectInfo?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_103, 0, 0, 0)
                        txtValueObjectInfo?.text = context.getString(R.string.not_provided)
                    }
                    AppConstants.OBJECT_ATTR_UNKNOWN -> {
                        txtValueObjectInfo?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_100, 0, 0, 0)
                        txtValueObjectInfo?.text = context.getString(R.string.unknown)
                    }
                }
            }
        }

    }

}