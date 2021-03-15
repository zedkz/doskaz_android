package com.zed.kz.doskaz.main.presentation.main.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.objects.main.MainObjectBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_seach.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class SearchFragment(val onSearchFragmentClosed: (ObjectItem) -> Unit, val openFilterFragment: () -> Unit): BaseFragment(), SearchFragmentView{

    companion object{

        val TAG = "SearchFragment"

        private val BUNDLE_CITY_ID = "city_id"

        fun newInstance(cityId: Int, onSearchFragmentClosed: (ObjectItem) -> Unit, openFilterFragment: () -> Unit): SearchFragment =
            SearchFragment(onSearchFragmentClosed, openFilterFragment).apply {
                arguments = Bundle().apply {
                    putInt(BUNDLE_CITY_ID, cityId)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        getKoin().getScopeOrNull(MainScope.SEARCH_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.SEARCH_SCOPE, named(MainScope.SEARCH_SCOPE))
        val cityId = arguments?.getInt(BUNDLE_CITY_ID)
        return scope.get { parametersOf(cityId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_seach

    private val filterAdapter = SearchAdapter{ presenter.onSearchItemSelected(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerSearch.adapter = filterAdapter
        imgClearSearch?.setOnClickListener { edtSearchSearch?.setText("") }
        edtSearchSearch?.showSoftInputOnFocus = true
        edtSearchSearch?.requestFocus()
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            toggleSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.SHOW_FORCED,
                0
            )
        }
        edtSearchSearch?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                presenter.filterObjects(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                imgClearSearch?.visible(!s.isNullOrEmpty())
            }
        })
        imgFilterSearch?.setOnClickListener {
            openFilterFragment.invoke()
            activity?.supportFragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        btnShowOnMapSearch?.setOnClickListener {
            presenter.showOnMapBtnClicked(edtSearchSearch.text.toString())
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.SEARCH_SCOPE)
        super.onDestroy()
    }

    override fun showObjectItemsData(dataList: List<ObjectItem>) {
        filterAdapter.submitData(dataList)
    }

    override fun openMainObjectBottomSheetFragment(objectId: Int) {
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        activity?.supportFragmentManager?.let {
            MainObjectBottomSheetFragment.newInstance(objectId)
                .show(it, MainObjectBottomSheetFragment.TAG)
        }
    }

    override fun closeThisFragmentWithResult(objectItem: ObjectItem) {
        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        onSearchFragmentClosed.invoke(objectItem)
        activity?.supportFragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}