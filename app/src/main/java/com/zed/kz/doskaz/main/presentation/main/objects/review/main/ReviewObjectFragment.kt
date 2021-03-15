package com.zed.kz.doskaz.main.presentation.main.objects.review.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_object_review.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ReviewObjectFragment(): BaseDialogFragment(), ReviewObjectFragmentView {

    companion object{

        val TAG = "ReviewFragment"

        fun newInstance(): ReviewObjectFragment =
            ReviewObjectFragment()
    }

    @InjectPresenter
    lateinit var objectPresenter: ReviewObjectPresenter

    @ProvidePresenter
    fun providePresenter(): ReviewObjectPresenter {
        getKoin().getScopeOrNull(MainScope.REVIEW_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.REVIEW_OBJECT_SCOPE, named(MainScope.REVIEW_OBJECT_SCOPE)).get()
    }

    private val adapter =
        ObjectReviewAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_object_review

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerObjectReview?.adapter = adapter
        objectPresenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.REVIEW_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showDataList(dataList: List<Review>) {
        adapter.submitData(dataList)
    }

}