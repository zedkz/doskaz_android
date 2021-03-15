package com.zed.kz.doskaz.main.presentation.auth.bonus

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import com.zed.kz.doskaz.main.presentation.profile.main.show.ShowProfileFragment
import kotlinx.android.synthetic.main.fragment_bonus.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class BonusFragment : BaseFragment(), BonusFragmentView{

    companion object{

        val TAG = "BonusFragment"

        fun newInstance(): BonusFragment =
            BonusFragment()
    }

    @InjectPresenter
    lateinit var presenter: BonusPresenter

    @ProvidePresenter
    fun providePresenter(): BonusPresenter {
        getKoin().getScopeOrNull(MainScope.BONUS_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.BONUS_SCOPE, named(MainScope.BONUS_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_bonus

    override fun setUp(savedInstanceState: Bundle?) {
        btnGoToProfileSms?.setOnClickListener { openShowProfileFragment() }
        btnAnotherTimeSms?.setOnClickListener { openHomeFragment() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.BONUS_SCOPE)
        super.onDestroy()
    }

    override fun openShowProfileFragment() {
        activity?.replaceFragment(
            R.id.container,
            ShowProfileFragment.newInstance(),
            ShowProfileFragment.TAG
        )
    }

    override fun openHomeFragment() {
        activity?.replaceFragment(
            R.id.container,
            HomeFragment.newInstance(),
            HomeFragment.TAG
        )
    }
}