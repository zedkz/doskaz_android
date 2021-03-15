package com.zed.kz.doskaz.main.presentation.main.objects.info.details

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_object_info_details.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ObjectInfoDetailsFragment : BaseDialogFragment(), ObjectInfoDetailsFragmentView{

    companion object{

        val TAG = "ObjectInfoDetailsFragment"

        private val BUNDLE_DATA_LIST = "data_list"

        fun newInstance(dataList: List<AddObject>): ObjectInfoDetailsFragment =
            ObjectInfoDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(BUNDLE_DATA_LIST, dataList.toTypedArray())
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ObjectInfoDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ObjectInfoDetailsPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_INFO_DETAILS_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.OBJECT_INFO_DETAILS_SCOPE, named(MainScope.OBJECT_INFO_DETAILS_SCOPE))
        val dataList = arguments?.getParcelableArray(BUNDLE_DATA_LIST)?.toList()
        return scope.get { parametersOf(dataList) }
    }

    private val adapter = ObjectInfoAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_object_info_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.apply {
            visible(true)
            setOnClickListener { dismiss() }
        }
        txtTitleToolbarMain?.text = getString(R.string.om_more_info)
        recyclerOFD?.adapter = adapter
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_INFO_DETAILS_SCOPE)
        super.onDestroy()
    }

    override fun showDataList(dataList: List<AddObject>) {
        adapter.submitData(dataList)
    }

}