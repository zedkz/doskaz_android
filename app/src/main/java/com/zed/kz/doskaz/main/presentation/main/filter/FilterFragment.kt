package com.zed.kz.doskaz.main.presentation.main.filter

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FilterFragment(private val onFilterResultListener: (params: Map<String, String>?) -> Unit): BaseFragment(), FilterFragmentView{

    companion object{

        val TAG = "FilterFragment"

        fun newInstance(onFilterResultListener: (params: Map<String, String>?) -> Unit): FilterFragment =
            FilterFragment(onFilterResultListener)
    }

    @InjectPresenter
    lateinit var presenter: FilterPresenter

    @ProvidePresenter
    fun providePresenter(): FilterPresenter {
        getKoin().getScopeOrNull(MainScope.FILTER_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.FILTER_SCOPE, named(MainScope.FILTER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_filter

    private val categoryAdapter = FilterCategoryAdapter{ presenter.onFilterItemSelected(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerFilter?.adapter = categoryAdapter
        presenter.onFirstInit()
        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener {
                presenter.onReadyBtnClicked(
                    isFullAccessible = checkboxFullAccessibleFilter.isChecked,
                    isPartialAccessible = checkboxPartialAccessibleFilter.isChecked,
                    isNotAccessible = checkboxNotAccessibleFilter.isChecked
                )
            }
        }
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.FILTER_SCOPE)
        super.onDestroy()
    }

    override fun showCategoriesData(dataList: List<Category>) {
        categoryAdapter.submitData(dataList)
    }

    override fun closeThisFragmentWithResult(params: Map<String, String>?) {
        onFilterResultListener.invoke(params)
        activity?.onBackPressed()
    }
}