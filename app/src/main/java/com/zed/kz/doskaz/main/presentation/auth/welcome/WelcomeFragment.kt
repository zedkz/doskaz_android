package com.zed.kz.doskaz.main.presentation.auth.welcome

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.WelcomeItem
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.category.DisabilityCategoryFragment
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class WelcomeFragment : BaseFragment(), WelcomeFragmentView{

    companion object{

        val TAG = "WelcomeFragment"

        fun newInstance(): WelcomeFragment =
            WelcomeFragment()
    }

    @InjectPresenter
    lateinit var presenter: WelcomePresenter

    @ProvidePresenter
    fun providePresenter(): WelcomePresenter {
        getKoin().getScopeOrNull(MainScope.WELCOME_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.WELCOME_SCOPE, named(MainScope.WELCOME_SCOPE)).get()
    }

    private val adapterWelcome = WelcomeAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_welcome

    override fun setUp(savedInstanceState: Bundle?) {
        viewPagerWelcome?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                presenter.onPageSelected(position)
            }
        })
        viewPagerWelcome.adapter = adapterWelcome
        presenter.onFirstInit()

        btnNextWelcome.setOnClickListener { presenter.onNextBtnClicked() }
        imgNextWelcome.setOnClickListener { presenter.onNextBtnClicked() }
        btnPreviousWelcome.setOnClickListener { presenter.onPreviousBtnClicked() }
        imgPreviousWelcome.setOnClickListener { presenter.onPreviousBtnClicked() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.WELCOME_SCOPE)
        super.onDestroy()
    }

    override fun showViewPagerData(dataList: List<WelcomeItem>) {
        adapterWelcome.submitData(dataList)
        viewPagerIndicatorWelcome.setupWithViewPager(viewPagerWelcome)
    }

    override fun setViewPagerPosition(position: Int) {
        viewPagerWelcome.currentItem = position
    }

    override fun openDisabilityCategoryFragment() {
        activity?.replaceFragment(
            R.id.container,
            DisabilityCategoryFragment.newInstance(),
            DisabilityCategoryFragment.TAG
        )
    }
}