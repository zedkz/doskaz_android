package com.zed.kz.doskaz.main.presentation.profile.my.comment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Comment
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.blog.details.BlogDetailsFragment
import kotlinx.android.synthetic.main.fragment_my_comment.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MyCommentFragment : BaseFragment(), MyCommentFragmentView {

    companion object{

        val TAG = "MyCommentFragment"

        fun newInstance(): MyCommentFragment =
            MyCommentFragment()
    }

    @InjectPresenter
    lateinit var presenter: MyCommentPresenter

    @ProvidePresenter
    fun providePresenter(): MyCommentPresenter {
        getKoin().getScopeOrNull(MainScope.MY_COMMENTS_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MY_COMMENTS_SCOPE, named(MainScope.MY_COMMENTS_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_my_comment

    private val adapter = MyCommentAdapter(
        { presenter.loadNextPage() },
        { it.postId?.let { it1 -> openBlogDetailsFragment(it1) } }
    )

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerMyComments.adapter = adapter
        spinnerSortMyComments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onSortItemSelected(position)
            }
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MY_COMMENTS_SCOPE)
        super.onDestroy()
    }

    override fun showComments(dataList: List<Comment>) {
        adapter.submitData(dataList)
    }

    override fun openBlogDetailsFragment(blogId: Int) {
        activity?.supportFragmentManager
            ?.addFragmentWithBackStack(
                R.id.container,
                BlogDetailsFragment.newInstance(blogId),
                BlogDetailsFragment.TAG
            )
    }
}