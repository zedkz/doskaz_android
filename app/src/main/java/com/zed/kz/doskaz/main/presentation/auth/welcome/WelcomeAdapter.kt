package com.zed.kz.doskaz.main.presentation.auth.welcome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.WelcomeItem
import kotlinx.android.synthetic.main.item_welcome.view.*

class WelcomeAdapter: PagerAdapter(){

    private var dataList: List<WelcomeItem> = listOf()

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == (obj as ConstraintLayout)
    }

    override fun getCount(): Int = dataList.size

    fun submitData(dataList: List<WelcomeItem>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = (container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.item_welcome, container, false)

        itemView.apply {
            imgWelcome.setImageResource(dataList[position].imageResourceId)
            txtTitleWelcome.text = dataList[position].title
            txtTextWelcome.text = dataList[position].text
        }

        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as ConstraintLayout)
    }
}