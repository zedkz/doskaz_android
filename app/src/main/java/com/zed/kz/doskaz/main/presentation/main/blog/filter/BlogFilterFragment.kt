package com.zed.kz.doskaz.main.presentation.main.blog.filter

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.BlogCategory
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_blog_filter.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class BlogFilterFragment(private val onFilterResultListener: (categoryId: Int) -> Unit): BaseFragment(), BlogFilterFragmentView{

    companion object{

        val TAG = "AboutFragment"

        fun newInstance(onFilterResultListener: (categoryId: Int) -> Unit): BlogFilterFragment =
            BlogFilterFragment(onFilterResultListener)
    }

    @InjectPresenter
    lateinit var presenter: BlogFilterPresenter

    @ProvidePresenter
    fun providePresenter(): BlogFilterPresenter {
        getKoin().getScopeOrNull(MainScope.BLOG_FILTER_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.BLOG_FILTER_SCOPE, named(MainScope.BLOG_FILTER_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_blog_filter

    private val adapter = BlogCategoryAdapter{ presenter.onItemSelected(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
        }
        recyclerBlogFilter?.adapter = adapter
        presenter.onFirstInit()
        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener { presenter.onReadyBtnClicked() }
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.BLOG_FILTER_SCOPE)
        super.onDestroy()
    }

    override fun showFilterData(dataList: List<BlogCategory>) {
        adapter.submitData(dataList)
    }

    override fun closeThisFragmentWithResult(categoryId: Int) {
        onFilterResultListener.invoke(categoryId)
        activity?.onBackPressed()
    }

}