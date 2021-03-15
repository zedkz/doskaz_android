package com.zed.kz.doskaz.main.presentation.objects.add.simple

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.SimpleObjectItem
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.views.ObjectBigEditTextView
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.AddObjectAdapter
import kotlinx.android.synthetic.main.item_simple_object.view.*
import timber.log.Timber

class SimpleObjectAdapter(val onItemSelectedListener: (SimpleObjectItem, AddObject) -> Unit) :
    RecyclerView.Adapter<SimpleObjectAdapter.MyViewHolder>(){

    private var dataList: MutableList<SimpleObjectItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_object, parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun submitData(dataList: MutableList<SimpleObjectItem>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){

        fun bind(simpleObjectItem: SimpleObjectItem){
            itemView.apply {
                imgAvailability1AOD.setImageResource(getAvailabilityDrawable(simpleObjectItem.availabilityZone?.movement ?: ""))
                imgAvailability2AOD.setImageResource(getAvailabilityDrawable(simpleObjectItem.availabilityZone?.limb ?: ""))
                imgAvailability3AOD.setImageResource(getAvailabilityDrawable(simpleObjectItem.availabilityZone?.vision ?: ""))
                imgAvailability4AOD.setImageResource(getAvailabilityDrawable(simpleObjectItem.availabilityZone?.hearing ?: ""))
                imgAvailability5AOD.setImageResource(getAvailabilityDrawable(simpleObjectItem.availabilityZone?.intellectual ?: ""))

                val adapter = AddObjectAdapter{ onItemSelectedListener.invoke(simpleObjectItem, it) }
                recyclerSimpleObject?.adapter = adapter
                adapter.submitData(simpleObjectItem.addObjectList)

                Timber.i("TYPEE=${simpleObjectItem.type}")

                when(simpleObjectItem.type){
                    AppConstants.ATTR_TYPE_ENTRANCE -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_1)))
                    }
                    AppConstants.ATTR_TYPE_PARKING -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_2)))
                    }
                    AppConstants.ATTR_TYPE_SERVICE -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_3)))
                    }
                    AppConstants.ATTR_TYPE_TOILET -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_4)))
                    }
                    AppConstants.ATTR_TYPE_NAVIGATION -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_5)))
                    }
                    AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_6)))
                    }
                    AppConstants.ATTR_TYPE_MOVEMENT -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_7)))
                    }
                    AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY -> {
                        obtCommentAOD?.setTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_8)))
                    }
                }

                obtCommentAOD?.onTextChangedListener = object : ObjectBigEditTextView.OnTextChangedListener{
                    override fun onTextChanged(text: String) {
                        simpleObjectItem.comment = if (text.isNotEmpty()) text else null
                    }
                }
            }
        }

    }

    private fun getAvailabilityDrawable(type: String): Int =
        when (type){
            AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE -> R.drawable.ic_45
            AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE -> R.drawable.ic_46
            AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE -> R.drawable.ic_63
            AppConstants.OVERALL_SCOPE_NOT_PROVIDED -> R.drawable.ic_105
            else -> R.drawable.ic_104
        }

}