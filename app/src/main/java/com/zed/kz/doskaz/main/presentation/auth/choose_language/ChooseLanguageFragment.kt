package com.zed.kz.doskaz.main.presentation.auth.choose_language

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.app.DoskazApp
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.activity.MainActivity
import com.zed.kz.doskaz.main.presentation.auth.welcome.WelcomeFragment
import kotlinx.android.synthetic.main.fragment_choose_language.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ChooseLanguageFragment : BaseFragment(), ChooseLanguageFragmentView{

    companion object{

        val TAG = "ChooseLanguageFragment"

        fun newInstance(): ChooseLanguageFragment =
            ChooseLanguageFragment()
    }

    @InjectPresenter
    lateinit var presenter: ChooseLanguagePresenter

    @ProvidePresenter
    fun providePresenter(): ChooseLanguagePresenter {
        getKoin().getScopeOrNull(MainScope.CHOOSE_LANGUAGE_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.CHOOSE_LANGUAGE_SCOPE, named(MainScope.CHOOSE_LANGUAGE_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_choose_language

    override fun setUp(savedInstanceState: Bundle?) {
        btnKzChooseLanguage.setOnClickListener { presenter.onKzBtnClicked() }
        btnRuChooseLanguage.setOnClickListener { presenter.onRuBtnClicked() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.CHOOSE_LANGUAGE_SCOPE)
        super.onDestroy()
    }

    override fun openWelcomeFragment() {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            WelcomeFragment.newInstance(),
            WelcomeFragment.TAG
        )
    }

    override fun changeLanguage() {
        context?.let {
            DoskazApp.localeManager?.setNewLocale(it, LocalStorage.getLang())
            activity?.startActivity(Intent(activity, MainActivity::class.java))
            System.exit(0)
        }
    }
}