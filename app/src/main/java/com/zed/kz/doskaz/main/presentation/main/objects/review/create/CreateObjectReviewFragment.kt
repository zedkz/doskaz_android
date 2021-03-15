package com.zed.kz.doskaz.main.presentation.main.objects.review.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_create_object_review.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class CreateObjectReviewFragment(val onNewReviewCreated: () -> Unit): BaseDialogFragment(), CreateObjectReviewFragmentView{

    companion object{

        val TAG = "CreateObjectReviewFragment"

        private val BUNDLE_BLOG_ID = "blog_id"
        private val BUNDLE_BLOG_PARENT_ID = "parent_id"

        fun newInstance(blogId: Int = -1, blogParentId: String? = null, onNewReviewCreated: () -> Unit): CreateObjectReviewFragment =
            CreateObjectReviewFragment(onNewReviewCreated).apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_BLOG_ID, blogId)
                    putString(BUNDLE_BLOG_PARENT_ID, blogParentId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: CreateObjectReviewPresenter

    @ProvidePresenter
    fun providePresenter(): CreateObjectReviewPresenter {
        getKoin().getScopeOrNull(MainScope.CREATE_OBJECT_REVIEW_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.CREATE_OBJECT_REVIEW_SCOPE, named(MainScope.CREATE_OBJECT_REVIEW_SCOPE))
        val blogId = arguments?.getInt(BUNDLE_BLOG_ID)
        val blogParentId = arguments?.getString(BUNDLE_BLOG_PARENT_ID)
        return scope.get { parametersOf(blogId, blogParentId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_create_object_review

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun setUp(savedInstanceState: Bundle?) {
        btnReadyToolbarMain?.apply {
            text = getString(R.string.send)
            setOnClickListener { presenter.onCreateBtnClicked(edtImpressionCOV?.text.toString()) }
            visible(true)
            isEnabled = false
            setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        }
        imgBackToolbarMain?.apply {
            setOnClickListener { this@CreateObjectReviewFragment.dismiss() }
            visible(true)
        }
        txtTitleToolbarMain?.text = getString(R.string.om_write_review)
        txtCountCOV?.text = getString(R.string.om_minimum_20_symbols, "20")

        edtImpressionCOV?.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                txtCountCOV?.text = getString(R.string.om_minimum_20_symbols,
                    (
                            if (s?.length ?: 0 < 20) {
                                txtCountCOV?.visible(true)
                                btnReadyToolbarMain?.apply {
                                    isEnabled = false
                                    setTextColor(
                                        ContextCompat.getColor(
                                            context,
                                            android.R.color.darker_gray
                                        )
                                    )
                                }
                                20 - (s?.length ?: 0)
                            } else {
                                txtCountCOV?.visible(false)
                                btnReadyToolbarMain?.apply {
                                    isEnabled = true
                                    setTextColor(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.colorText
                                        )
                                    )
                                }
                                0
                            }
                            ).toString()  )
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.CREATE_OBJECT_REVIEW_SCOPE)
        super.onDestroy()
    }

    override fun closeThisFragment() {
        dismiss()
        onNewReviewCreated.invoke()
    }
}