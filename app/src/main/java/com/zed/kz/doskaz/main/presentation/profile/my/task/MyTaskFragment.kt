package com.zed.kz.doskaz.main.presentation.profile.my.task

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Task
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_my_task.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MyTaskFragment : BaseFragment(), MyTaskFragmentView {

    companion object{

        val TAG = "MyTaskFragment"

        fun newInstance(): MyTaskFragment =
            MyTaskFragment()
    }

    @InjectPresenter
    lateinit var presenter: MyTaskPresenter

    @ProvidePresenter
    fun providePresenter(): MyTaskPresenter {
        getKoin().getScopeOrNull(MainScope.MY_TASK_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MY_TASK_SCOPE, named(MainScope.MY_TASK_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_my_task

    private val adapter = MyTaskAdapter{ presenter.loadNextPage() }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerMyTask.adapter = adapter
        spinnerSortMyTask.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
        getKoin().getScopeOrNull(MainScope.MY_TASK_SCOPE)
        super.onDestroy()
    }

    override fun showTasks(dataList: List<Task>) {
        adapter.submitData(dataList)
    }
}