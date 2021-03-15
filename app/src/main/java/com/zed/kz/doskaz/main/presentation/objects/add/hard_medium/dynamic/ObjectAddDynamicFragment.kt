package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogFragment
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.AddObjectAdapter
import kotlinx.android.synthetic.main.fragment_add_object_dynamic.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import kotlinx.android.synthetic.main.include_zone_score.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ObjectAddDynamicFragment(val onCloseWithResult: () -> Unit): BaseFragment(), ObjectAddDynamicFragmentView{

    companion object{

        var TAG = "ObjectAddDynamicFragment"

        private val BUNDLE_LIST = "list"
        private val BUNDLE_TYPE = "type"

        fun newInstance(type: String, dataList: List<AddObject>,  onCloseWithResult: () -> Unit): ObjectAddDynamicFragment = ObjectAddDynamicFragment(onCloseWithResult).apply {
            arguments = Bundle().apply {
                putParcelableArray(BUNDLE_LIST, dataList.toTypedArray())
                putString(BUNDLE_TYPE, type)
            }
        }

    }

    @InjectPresenter
    lateinit var presenter: ObjectAddDynamicPresenter

    private val adapter = AddObjectAdapter{ presenter.onContentItemSelected(it) }

    @ProvidePresenter
    fun providePresenter(): ObjectAddDynamicPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_DYNAMIC_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.OBJECT_ADD_DYNAMIC_SCOPE, named(MainScope.OBJECT_ADD_DYNAMIC_SCOPE))
        val dataList = arguments?.getParcelableArray(BUNDLE_LIST)?.toList()
        val type = arguments?.getString(BUNDLE_TYPE)
        return scope.get { parametersOf(type, dataList) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_add_object_dynamic

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerOAD?.adapter = adapter
        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener { presenter.onReadyBtnClicked(obtCommentAOD?.getValue() ?: "") }
        }
        btnResetAOD?.setOnClickListener { presenter.onResetBtnClicked() }
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_DYNAMIC_SCOPE)
        super.onDestroy()
    }

    override fun showUIData(dataList: List<AddObject>) {
        adapter.submitData(dataList)
    }

    override fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun showTitle(title: String) {
        txtTitleToolbarMain?.visible(true)
        txtTitleToolbarMain?.text = title
    }

    override fun openListDialogFragment(dataList: List<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(ArrayList(dataList))
            { presenter.onListItemSelected(it) }.show(it, ListDialogFragment.TAG)
        }
    }

    override fun closeThisFragmentWithResult() {
        onCloseWithResult.invoke()
        activity?.onBackPressed()
        showProgressBar(false)
    }

    override fun showAvailabilityImages(
        drawable1: Int,
        drawable2: Int,
        drawable3: Int,
        drawable4: Int,
        drawable5: Int
    ) {
        imgAvailability1AOD.setImageResource(drawable1)
        imgAvailability2AOD.setImageResource(drawable2)
        imgAvailability3AOD.setImageResource(drawable3)
        imgAvailability4AOD.setImageResource(drawable4)
        imgAvailability5AOD.setImageResource(drawable5)
    }

    override fun showAvailabilityTitle(title: String) {
        obtCommentAOD?.setTitle(title)
    }
}