package com.zed.kz.doskaz.global.views

import android.content.Context
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.view_text_object.view.*

class ObjectTextView: ConstraintLayout{

    constructor(context: Context): super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){ init(attrs) }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){ init(attrs) }

    private fun init(attrs: AttributeSet?){

        val title: String?
        val value: String?
        val hint: String?
        val showQuestion: Boolean?
        val dropDownIcon: Int?

        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ObjectTextView)

        try{
            title = attrArray.getString(R.styleable.ObjectTextView_title)
            value = attrArray.getString(R.styleable.ObjectTextView_value)
            hint = attrArray.getString(R.styleable.ObjectTextView_hint)
            showQuestion = attrArray.getBoolean(R.styleable.ObjectTextView_showQuestion, false)
            dropDownIcon = attrArray.getResourceId(R.styleable.ObjectTextView_dropDownIcon, R.drawable.ic_67)
        }finally {
            attrArray.recycle()
        }

        View.inflate(context, R.layout.view_text_object, this)

        setTitle(title)
        setValue(value)
        setHint(hint)
        showQuestionIcon(showQuestion)
        setDropDownIcon(dropDownIcon)
    }

    fun setTitle(title: String?){
        txtTitleViewObjectBig.text = title
    }

    fun setTitle(title: Spanned?){
        txtTitleViewObjectBig.text = title
    }

    fun setValue(value: String?){
        showError(false)
        txtValueViewObjectBig.text = value
    }

    fun getValue(): String =
        txtValueViewObjectBig.text.toString()

    fun setHint(hint: String?){
        txtValueViewObjectBig.hint = hint
    }

    fun showError(show: Boolean){
        txtErrorViewObjectBig.visible(show)
        if (show){
            txtValueViewObjectBig.setBackgroundResource(R.drawable.skin_trans_border_red)
        }else{
            txtValueViewObjectBig.setBackgroundResource(R.drawable.skin_trans_border_grey_padding)
        }
    }

    fun showQuestionIcon(show: Boolean?){
        imgQuestionViewObjectBig.visible(show)
    }

    fun setDropDownIcon(icon: Int?){
        icon?.let { imgDropDownViewObjectBig.setImageResource(it) }
    }

    fun isDataValid(scrollView: NestedScrollView? = null, parentView: ViewGroup? = null): Boolean{
        return if (getValue().isEmpty()){
            if (scrollView != null && parentView != null){
                var scrollToPosition = -1
                for (i in 0..parentView.childCount){
                    if (parentView.getChildAt(i) == this) {
                        scrollToPosition = (parentView.getChildAt(i).parent as View).top + parentView.getChildAt(i).top
                        break
                    }
                }
                if (scrollToPosition != -1)
                    scrollView.smoothScrollTo(0, scrollToPosition)
            }
            showError(true)
            false
        }else{
            true
        }
    }

}