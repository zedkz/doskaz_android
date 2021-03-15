package com.zed.kz.doskaz.global.views

import android.content.Context
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.view_edit_text_object.view.*
import timber.log.Timber

class ObjectEditTextView: ConstraintLayout{

    private var objectEditTextListeners: ObjectEditTextListeners? = null
    private var isRequired: Boolean = false

    constructor(context: Context): super(context) { init(null) }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){ init(attrs) }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){ init(attrs) }

    private fun init(attrs: AttributeSet?){

        val title: String?
        val value: String?
        val hint: String?
        val showQuestion: Boolean?
        val questionIcon: Int?
        val showDropDownIcon: Boolean?

        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ObjectEditTextView)

        try{
            title = attrArray.getString(R.styleable.ObjectEditTextView_title)
            value = attrArray.getString(R.styleable.ObjectEditTextView_value)
            hint = attrArray.getString(R.styleable.ObjectEditTextView_hint)
            showQuestion = attrArray.getBoolean(R.styleable.ObjectEditTextView_showQuestion, false)
            questionIcon = attrArray.getResourceId(R.styleable.ObjectEditTextView_questionIcon, R.drawable.ic_66)
            showDropDownIcon = attrArray.getBoolean(R.styleable.ObjectEditTextView_showDropDownIcon, false)
            isRequired = attrArray.getBoolean(R.styleable.ObjectEditTextView_isRequired, false)
        }finally {
            attrArray.recycle()
        }

        View.inflate(context, R.layout.view_edit_text_object, this)

        setTitle(title)
        setValue(value)
        setHint(hint)
        showQuestionIcon(showQuestion)
        setQuestionIcon(questionIcon)
        showDropDownIcon(showDropDownIcon)

        imgQuestionEditViewObject.setOnClickListener { objectEditTextListeners?.onQuestionIconClicked() }
        edtValueEditViewObject.addTextChangedListener(
            object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    showError(false)
                    s?.toString()?.let { objectEditTextListeners?.onValueChanged(it) }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            }
        )
    }

    fun setTitle(title: String?){
        txtTitleEditViewObject.text = title
    }

    fun setTitle(title: Spanned?){
        txtTitleEditViewObject.text = title
    }

    fun setValue(value: String?){
        showError(false)
        edtValueEditViewObject.setText(value)
    }

    fun getValue(): String =
        edtValueEditViewObject.text.toString()

    fun setHint(hint: String?){
        edtValueEditViewObject.hint = hint
    }

    fun showError(show: Boolean){
        txtErrorEditViewObject.visible(show)
        if (show){
            edtValueEditViewObject.setBackgroundResource(R.drawable.skin_trans_border_red)
        }else{
            edtValueEditViewObject.setBackgroundResource(R.drawable.skin_trans_border_grey_padding)
        }
    }

    fun showQuestionIcon(show: Boolean?){
        imgQuestionEditViewObject.visible(show)
    }

    fun setQuestionIcon(icon: Int?){
        icon?.let { imgQuestionEditViewObject.setImageResource(it) }
    }

    fun showDropDownIcon(show: Boolean?){
        imgDropDownEditViewObject.visible(show)
    }

    fun setOnObjectEditTextListeners(objectEditTextListeners: ObjectEditTextListeners){
        this.objectEditTextListeners = objectEditTextListeners
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
            showError(false)
            true
        }
    }

    interface ObjectEditTextListeners{
        fun onQuestionIconClicked()
        fun onValueChanged(value: String)
    }

}