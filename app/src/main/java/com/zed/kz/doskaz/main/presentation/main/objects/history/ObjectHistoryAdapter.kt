package com.zed.kz.doskaz.main.presentation.main.objects.history

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.History
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.utils.AppConstants
import kotlinx.android.synthetic.main.item_object_history.view.*

class ObjectHistoryAdapter(): RecyclerView.Adapter<ObjectHistoryAdapter.HistoryHolder>(){

    private var dataList: List<History> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder =
        HistoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_object_history, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: List<History>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class HistoryHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(history: History){
            itemView.apply {
                txtDateObjectHistory?.text = history.date?.getFormattedDateT()

                val text = when(history.datas?.type){
                    AppConstants.HISTORY_TYPE_REVIEW_CREATED -> context.getString(R.string.om_history_review, "<b>${history.name}</b>")
                    AppConstants.HISTORY_TYPE_VERIFICATION_CONFIRMED -> context.getString(R.string.om_history_verified, "<b>${history.name}</b>")
                    AppConstants.HISTORY_TYPE_VERIFICATION_REJECTED -> context.getString(R.string.om_history_not_verified, "<b>${history.name}</b>")
                    AppConstants.HISTORY_TYPE_SUPPLEMENTED -> context.getString(R.string.om_history_supplemented, "<b>${history.name}</b>")
                    else -> ""
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtTextObjectHistory?.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
                }else{
                    txtTextObjectHistory?.text = Html.fromHtml(text)
                }

            }
        }

    }

}