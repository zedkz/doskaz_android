package com.zed.kz.doskaz.main.presentation.dialog.list

import android.os.Bundle
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.dialog_fragment_list.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ListDialogFragment(val onItemSelectedListener: (ListItem) -> Unit): BaseDialogFragment(), ListDialogFragmentView{

    companion object{

        val TAG = "ListDialogFragment"

        private val BUNDLE_LIST_ITEMS = "list_items"

        fun newInstance(dataList: ArrayList<ListItem>, onItemSelectedListener: (ListItem) -> Unit): ListDialogFragment =
            ListDialogFragment(onItemSelectedListener).apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(BUNDLE_LIST_ITEMS, dataList)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ListDialogPresenter

    @ProvidePresenter
    fun providePresenter(): ListDialogPresenter {
        getKoin().getScopeOrNull(MainScope.LIST_DIALOG_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.LIST_DIALOG_SCOPE, named(MainScope.LIST_DIALOG_SCOPE))
        val dataList: List<ListItem>? = arguments?.getParcelableArrayList<ListItem>(BUNDLE_LIST_ITEMS)?.toList()
        return scope.get { parametersOf(dataList) }
    }

    override val layoutRes: Int
        get() = R.layout.dialog_fragment_list

    private val listAdapter = ListAdapter{ closeThisFragmentWithResult(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerDFL.adapter = listAdapter
        presenter.onFirstInit()
        btnReadyDFL.setOnClickListener { presenter.onReadyBtnClicked() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.apply {
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.LIST_DIALOG_SCOPE)
        super.onDestroy()
    }

    override fun showData(dataList: List<ListItem>) {
        listAdapter.submitData(dataList)
    }

    override fun closeThisFragmentWithResult(listItem: ListItem) {
        onItemSelectedListener.invoke(listItem)
        dismiss()
    }
}