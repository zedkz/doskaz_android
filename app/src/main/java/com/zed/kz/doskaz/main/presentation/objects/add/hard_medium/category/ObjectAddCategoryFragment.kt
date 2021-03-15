package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.category

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.AddObjectCategoryAdapter
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.common.ObjectAddCommonFragment
import com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic.ObjectAddDynamicFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.AddSimpleObjectBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_add_object_medium_category.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ObjectAddCategoryFragment : BaseFragment(),
    ObjectAddCategoryFragmentView {

    companion object{

        private lateinit var instance : ObjectAddCategoryFragment

        private val BUNDLE_TYPE = "type"

        fun newInstance(type: String): ObjectAddCategoryFragment {
            instance = ObjectAddCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_TYPE, type)
                }
            }
            return instance
        }


        fun getInstance(): ObjectAddCategoryFragment? {
            return if (this::instance.isInitialized){
                instance
            }else
                null
        }
    }

    val TAG = "ObjectAddMediumCategoryFragment${System.currentTimeMillis()}"

    @InjectPresenter
    lateinit var presenter: ObjectAddCategoryPresenter

    private val adapter = AddObjectCategoryAdapter{ presenter.onCategoryItemSelected(it) }

    @ProvidePresenter
    fun providePresenter(): ObjectAddCategoryPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_MEDIUM_CATEGORY_SCOPE)?.close()
        val type = arguments?.getString(BUNDLE_TYPE)
        val scope = getKoin().getOrCreateScope(MainScope.OBJECT_ADD_MEDIUM_CATEGORY_SCOPE, named(MainScope.OBJECT_ADD_MEDIUM_CATEGORY_SCOPE))
        return scope.get { parametersOf(type) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_add_object_medium_category

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerAOMC?.adapter = adapter
        presenter.onFirstInit()
        imgNextAOC?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.addFragmentWithBackStack(
                R.id.container,
                newInstance(AppConstants.FORM_TYPE_FULL),
                getInstance()?.TAG ?: ""
            )
        }

        imgPreviousAOC?.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack(TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.addFragmentWithBackStack(
                R.id.container,
                AddSimpleObjectBottomSheetFragment.newInstance(),
                AddSimpleObjectBottomSheetFragment.TAG
            )
        }

        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.add_object)
        }
        btnReadyToolbarMain?.apply {
            visible(true)
            setOnClickListener { presenter.onReadyBtnClicked() }
        }

    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_MEDIUM_CATEGORY_SCOPE)
        super.onDestroy()
    }

    override fun showCategoryData(dataList: List<Category>) {
        adapter.submitData(dataList)
    }

    override fun openAddObjectCommonFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ObjectAddCommonFragment.newInstance{ presenter.onCommonFragmentResult() },
            ObjectAddCommonFragment.TAG
        )
    }

    override fun openAddObjectDynamicFragment(type: String, dataList: List<AddObject>) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ObjectAddDynamicFragment.newInstance(type, dataList) { presenter.onDynamicFragmentResult() } ,
            ObjectAddDynamicFragment.TAG
        )
    }

    override fun showFormTitle(title: String) {
        txtFormTitleAOC?.text = title
    }

    override fun closeThisFragment() {
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .sendBroadcast(Intent(AppConstants.FILTER_OBJECT_CREATED))
        }
        activity?.onBackPressed()
    }

}