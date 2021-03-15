package com.zed.kz.doskaz.main.presentation.profile.my.objects

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.MyObject
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_my_objects.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MyObjectsFragment : BaseFragment(), MyObjectsFragmentView {

    companion object{

        val TAG = "MyObjectsFragment"

        fun newInstance(): MyObjectsFragment =
            MyObjectsFragment()
    }

    @InjectPresenter
    lateinit var presenter: MyObjectsPresenter

    @ProvidePresenter
    fun providePresenter(): MyObjectsPresenter {
        getKoin().getScopeOrNull(MainScope.MY_OBJECTS_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MY_OBJECTS_SCOPE, named(MainScope.MY_OBJECTS_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_my_objects

    private val adapter = MyObjectsAdapter{ presenter.loadNextPage() }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerMyObjects.adapter = adapter
        spinnerSortMyObjects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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

        spinnerOverallScopeMyObjects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                presenter.onOverallItemSelected(position)
            }
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MY_OBJECTS_SCOPE)
        super.onDestroy()
    }

    override fun showObjects(dataList: List<MyObject>) {
        adapter.submitData(dataList)
    }
}