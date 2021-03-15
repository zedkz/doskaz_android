package com.zed.kz.doskaz.main.presentation.main.blog.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.blog.details.BlogDetailsFragment
import com.zed.kz.doskaz.main.presentation.main.blog.filter.BlogFilterFragment
import kotlinx.android.synthetic.main.fragment_blog_list.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class BlogListFragment(): BaseFragment(), BlogListFragmentView{

    companion object{

        val TAG = "BlogListFragment"

        fun newInstance(): BlogListFragment =
            BlogListFragment()
    }

    @InjectPresenter
    lateinit var presenter: BlogListPresenter

    @ProvidePresenter
    fun providePresenter(): BlogListPresenter {
        getKoin().getScopeOrNull(MainScope.BLOG_LIST_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.BLOG_LIST_SCOPE, named(MainScope.BLOG_LIST_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_blog_list

    private val adapter = BlogAdapter(
        { presenter.loadNextPage() },
        { presenter.onBlogItemClicked(it) }
    )

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerBlogList?.adapter = adapter
        presenter.onFirstInit()

        imgBackToolbarMain?.apply {
            visible(true)
            setOnClickListener { activity?.onBackPressed() }
        }

        txtTitleToolbarMain?.text = getString(R.string.blog)
        imgClearSearchBlogList?.setOnClickListener {
            if (!edtSearchBlogList?.text?.toString().isNullOrEmpty())
                edtSearchBlogList?.setText("")
        }

        edtSearchBlogList?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                presenter.onSearch(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imgClearSearchBlogList?.visible(!s.isNullOrEmpty())
            }
        })

        imgFilterBlogList?.setOnClickListener {
            activity?.supportFragmentManager
                ?.addFragmentWithBackStack(
                    R.id.container,
                    BlogFilterFragment.newInstance{ presenter.onFilterResult(it) },
                    BlogFilterFragment.TAG
                )
        }
    }


    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.BLOG_LIST_SCOPE)
        super.onDestroy()
    }

    override fun showBlogData(dataList: List<Blog>) {
        adapter.submitData(dataList)
    }

    override fun openBlogDetailsFragment(blogId: Int) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            BlogDetailsFragment.newInstance(blogId),
            BlogDetailsFragment.TAG
        )
    }
}