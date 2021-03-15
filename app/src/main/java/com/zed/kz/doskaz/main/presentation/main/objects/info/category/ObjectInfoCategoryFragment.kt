package com.zed.kz.doskaz.main.presentation.main.objects.info.category

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.objects.info.details.ObjectInfoDetailsFragment
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.AddObjectCategoryAdapter
import kotlinx.android.synthetic.main.fragment_object_info_category.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ObjectInfoCategoryFragment : BaseDialogFragment(), ObjectInfoCategoryFragmentView {

    companion object{

        val TAG = "ObjectAddMediumCategoryFragment${System.currentTimeMillis()}"

        fun newInstance(): ObjectInfoCategoryFragment =
            ObjectInfoCategoryFragment()

    }

    @InjectPresenter
    lateinit var presenter: ObjectInfoCategoryPresenter

    private val adapter = AddObjectCategoryAdapter{ presenter.onCategoryItemSelected(it) }

    @ProvidePresenter
    fun providePresenter(): ObjectInfoCategoryPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_INFO_CATEGORY_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.OBJECT_INFO_CATEGORY_SCOPE, named(MainScope.OBJECT_INFO_CATEGORY_SCOPE))
        return scope.get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_object_info_category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.apply {
            visible(true)
            setOnClickListener { dismiss() }
        }
        txtTitleToolbarMain?.text = getString(R.string.om_more_info)
        recyclerOIC?.adapter = adapter
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_INFO_CATEGORY_SCOPE)
        super.onDestroy()
    }

    override fun showCategoryData(dataList: List<Category>) {
        adapter.submitData(dataList)
    }

    override fun openInfoDetailsFragment(dataList: List<AddObject>) {
        activity?.supportFragmentManager?.let {
            ObjectInfoDetailsFragment.newInstance(dataList)
                .show(it, ObjectInfoDetailsFragment.TAG)
        }
    }
}