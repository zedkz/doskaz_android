package com.zed.kz.doskaz.main.presentation.main.blog.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.gson.Gson
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Blog
import com.zed.kz.doskaz.entity.BlogComment
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.getFormattedDateT
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.objects.review.create.CreateObjectReviewFragment
import kotlinx.android.synthetic.main.fragment_blog_details.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import timber.log.Timber

class BlogDetailsFragment : BaseFragment(), BlogDetailsFragmentView{

    companion object{

        val TAG = "BlogDetailsFragment"

        private val BUNDLE_BLOG_ID = "blog_id"

        fun newInstance(blogId: Int): BlogDetailsFragment =
            BlogDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_BLOG_ID, blogId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: BlogDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): BlogDetailsPresenter {
        getKoin().getScopeOrNull(MainScope.BLOG_DETAILS_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.BLOG_DETAILS_SCOPE, named(MainScope.BLOG_DETAILS_SCOPE))
        val blogId = arguments?.getInt(BUNDLE_BLOG_ID)
        return scope.get { parametersOf(blogId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_blog_details

    private val similarAdapter = BlogSimilarAdapter{ presenter.onSimilarItemClicked(it) }
    private val commentAdapter = BlogCommentAdapter{ presenter.onCommentReplyBtnClicked(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
        }
        recyclerSimilarBlogDetails?.adapter = similarAdapter
        recyclerCommentBlogDetails?.adapter = commentAdapter
        presenter.onFirstInit()

        spinnerSortCommentsBlogDetails.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        btnCreateCommentBlogDetails?.setOnClickListener { presenter.onCreateCommentBtnClicked() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.BLOG_DETAILS_SCOPE)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showBlogInfo(blog: Blog) {
        txtTitleToolbarMain?.text = blog.title
        txtTitleBlogDetails?.text = blog.title
        txtDateCategoryBlogDetails?.text = "${blog.publishedAt?.getFormattedDateT()}    ${blog.categoryTitle}"
        imgMainBlogDetails?.setImageUrl(blog.image, R.drawable.no_image_large)
        txtDescriptionBlogDetails?.text = Html.fromHtml(blog.annotation, Html.FROM_HTML_MODE_LEGACY)
    }

    override fun showSimilarBlog(dataList: List<Blog>) {
        similarAdapter.submitData(dataList)
    }

    override fun openBlogDetailsFragment(id: Int) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            newInstance(id),
            TAG
        )
    }

    override fun showComments(dataList: List<BlogComment>) {
        commentAdapter.submitData(dataList)
    }

    override fun showCommentCount(count: Int) {
        txtCommentCountBlogDetails?.text = getString(R.string.blog_comment, count.toString())
    }

    override fun openCreateObjectReviewFragment(blogId: Int, parentId: String?) {
        activity?.supportFragmentManager?.let {
            CreateObjectReviewFragment.newInstance(blogId, parentId) { presenter.refreshBlogComments() }
                .show(it, CreateObjectReviewFragment.TAG)
        }
    }

    override fun openSignInFragment() {
        activity?.supportFragmentManager
            ?.addFragmentWithBackStack(
                R.id.container,
                SignInFragment.newInstance(),
                SignInFragment.TAG
            )
    }
}