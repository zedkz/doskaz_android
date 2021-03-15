package com.zed.kz.doskaz.global.views

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.view_edit_text_object_big.view.*

class ObjectBigEditTextView: ConstraintLayout{

    var onTextChangedListener: OnTextChangedListener? = null

    constructor(context: Context): super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){ init(attrs) }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){ init(attrs) }

    private fun init(attrs: AttributeSet?){

        val title: String?
        val value: String?
        val hint: String?
        val showQuestion: Boolean?

        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ObjectBigEditTextView)

        try{
            title = attrArray.getString(R.styleable.ObjectBigEditTextView_title)
            value = attrArray.getString(R.styleable.ObjectBigEditTextView_value)
            hint = attrArray.getString(R.styleable.ObjectBigEditTextView_hint)
            showQuestion = attrArray.getBoolean(R.styleable.ObjectBigEditTextView_showQuestion, false)
        }finally {
            attrArray.recycle()
        }

        View.inflate(context, R.layout.view_edit_text_object_big, this)

        setTitle(title)
        setValue(value)
        setHint(hint)
        showQuestionIcon(showQuestion)

        txtValueViewBigObject.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    showError(false)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onTextChangedListener?.onTextChanged(s.toString())
                }
            }
        )
    }

    fun setTitle(title: String?){
        txtTitleViewBigObject.text = title
    }

    fun setTitle(title: Spanned?){
        txtTitleViewBigObject.text = title
    }

    fun setValue(value: String?){
        showError(false)
        txtValueViewBigObject.setText(value)
    }

    fun getValue(): String =
        txtValueViewBigObject.text.toString()

    fun setHint(hint: String?){
        txtValueViewBigObject.hint = hint
    }

    fun showError(show: Boolean){
        txtErrorViewBigObject.visible(show)
        if (show){
            txtValueViewBigObject.setBackgroundResource(R.drawable.skin_trans_border_red)
        }else{
            txtValueViewBigObject.setBackgroundResource(R.drawable.skin_trans_border_grey_padding)
        }
    }

    fun showQuestionIcon(show: Boolean?){
        imgQuestionViewBigObject.visible(show)
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

    interface OnTextChangedListener{

        fun onTextChanged(text: String)
    }

}