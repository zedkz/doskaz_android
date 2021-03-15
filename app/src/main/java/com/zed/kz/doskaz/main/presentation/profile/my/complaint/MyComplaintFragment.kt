package com.zed.kz.doskaz.main.presentation.profile.my.complaint

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Complaint
import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_my_comment.*
import kotlinx.android.synthetic.main.fragment_my_complaints.*
import kotlinx.android.synthetic.main.fragment_my_task.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MyComplaintFragment : BaseFragment(), MyComplaintFragmentView {

    companion object{

        val TAG = "MyComplaintFragment"

        fun newInstance(): MyComplaintFragment =
            MyComplaintFragment()
    }

    @InjectPresenter
    lateinit var presenter: MyComplaintPresenter

    @ProvidePresenter
    fun providePresenter(): MyComplaintPresenter {
        getKoin().getScopeOrNull(MainScope.MY_COMPLAINT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MY_COMPLAINT_SCOPE, named(MainScope.MY_COMPLAINT_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_my_complaints

    private val adapter = MyComplaintAdapter{ presenter.loadNextPage() }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerMyComplaint.adapter = adapter
        spinnerSortMyComplaint.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        getKoin().getScopeOrNull(MainScope.MY_COMPLAINT_SCOPE)
        super.onDestroy()
    }

    override fun showComplaints(dataList: List<Complaint>) {
        adapter.submitData(dataList)
    }
}