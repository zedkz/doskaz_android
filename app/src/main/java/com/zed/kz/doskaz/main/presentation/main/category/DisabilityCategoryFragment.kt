package com.zed.kz.doskaz.main.presentation.main.category

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.DisabilityCategory
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_disability_category.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class DisabilityCategoryFragment : BaseFragment(), DisabilityCategoryFragmentView{

    companion object{

        val TAG = "DisabilityCategoryFragment"

        val BUNDLE_FROM_SETTINGS = "from_settings"

        fun newInstance(isFromSettings: Boolean = false): DisabilityCategoryFragment =
            DisabilityCategoryFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(BUNDLE_FROM_SETTINGS, isFromSettings)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: DisabilityCategoryPresenter

    @ProvidePresenter
    fun providePresenter(): DisabilityCategoryPresenter {
        getKoin().getScopeOrNull(MainScope.DISABILITY_CATEGORY_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.DISABILITY_CATEGORY_SCOPE, named(MainScope.DISABILITY_CATEGORY_SCOPE))
        val isFromSettings = arguments?.get(BUNDLE_FROM_SETTINGS)
        return scope.get { parametersOf(isFromSettings) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_disability_category

    private val adapter = DisabilityCategoryAdapter{ presenter.onDisabilityCategorySelected(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        txtTitleToolbar?.text = getString(R.string.person_category)
        recyclerDisabilityCategory.adapter = adapter
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.DISABILITY_CATEGORY_SCOPE)
        super.onDestroy()
    }

    override fun showData(dataList: List<DisabilityCategory>) {
        adapter.submitData(dataList)
    }

    override fun openHomeFragment() {
        activity?.supportFragmentManager
            ?.replaceFragment(
                R.id.container,
                HomeFragment.newInstance(),
                HomeFragment.TAG
            )
    }

    override fun closeThisFragment() {
        activity?.onBackPressed()
    }

}