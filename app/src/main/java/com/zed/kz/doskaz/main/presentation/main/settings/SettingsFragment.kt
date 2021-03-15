package com.zed.kz.doskaz.main.presentation.main.settings

import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.app.DoskazApp
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.Settings
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.activity.MainActivity
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogFragment
import com.zed.kz.doskaz.main.presentation.main.category.DisabilityCategoryFragment
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class SettingsFragment : BaseFragment(), SettingsFragmentView{

    companion object{

        val TAG = "SettingsFragment"

        fun newInstance(): SettingsFragment =
            SettingsFragment()
    }

    @InjectPresenter
    lateinit var presenter: SettingsPresenter

    @ProvidePresenter
    fun providePresenter(): SettingsPresenter {
        getKoin().getScopeOrNull(MainScope.SETTINGS_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.SETTINGS_SCOPE, named(MainScope.SETTINGS_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_settings

    private val adapter = SettingsAdapter{ presenter.onSettingsItemSelected(it) }

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.settings)
        }
        recyclerSettings?.adapter = adapter
        presenter.onFirstInit()
    }

    override fun showData(dataList: List<Settings>) {
        adapter.submitData(dataList)
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.SETTINGS_SCOPE)
        super.onDestroy()
    }

    override fun openDisabilityCategoryFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            DisabilityCategoryFragment.newInstance(true),
            DisabilityCategoryFragment.TAG
        )
    }

    override fun openListDialogFragment(dataList: List<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(ArrayList(dataList)) { presenter.onListItemSelected(it) }
                .show(
                    it,
                    ListDialogFragment.TAG
                )
        }
    }

    override fun openLangDialogFragment(dataList: ArrayList<String>) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.settings_language))
                .setItems(dataList.toTypedArray())
                { dialog, which ->
                    presenter.onLangItemSelected(dialog, which)
                }
                .show()
        }
    }

    override fun changeLanguage() {
        context?.let {
            DoskazApp.localeManager?.setNewLocale(it, LocalStorage.getLang())
            activity?.startActivity(Intent(activity, MainActivity::class.java))
            System.exit(0)
        }
    }
}