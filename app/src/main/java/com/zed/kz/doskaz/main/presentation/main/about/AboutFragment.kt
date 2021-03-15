package com.zed.kz.doskaz.main.presentation.main.about

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AboutFragment : BaseFragment(), AboutFragmentView{

    companion object{

        val TAG = "AboutFragment"

        fun newInstance(): AboutFragment =
            AboutFragment()
    }

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    @ProvidePresenter
    fun providePresenter(): AboutPresenter {
        getKoin().getScopeOrNull(MainScope.ABOUT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.ABOUT_SCOPE, named(MainScope.ABOUT_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_about

    override fun setUp(savedInstanceState: Bundle?) {
        flow11?.apply {
            text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(getString(R.string.about_11), Html.FROM_HTML_MODE_LEGACY)
            }else {
                Html.fromHtml(getString(R.string.about_11))
            }
            setTextSize(resources.getDimension(R.dimen.flow_text_size))
            setTypeface(Typeface.create("lato_regular.ttf", Typeface.NORMAL))
        }

        flow12?.apply {
            text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(getString(R.string.about_12), Html.FROM_HTML_MODE_LEGACY)
            }else {
                Html.fromHtml(getString(R.string.about_12))
            }
            setTextSize(resources.getDimension(R.dimen.flow_text_size))
            setTypeface(Typeface.create("lato_regular.ttf", Typeface.NORMAL))
        }

        flow13?.apply {
            text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(getString(R.string.about_13), Html.FROM_HTML_MODE_LEGACY)
            }else {
                Html.fromHtml(getString(R.string.about_13))
            }
            setTextSize(resources.getDimension(R.dimen.flow_text_size))
            setTypeface(Typeface.create("lato_regular.ttf", Typeface.NORMAL))
        }
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.about)
        }
        image8?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ef-ca.kz/")))
        }
        image9?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.oft.kz/")))
        }
        image10?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/etshkz/")))
        }
        image11?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eeas.europa.eu/delegations/kazakhstan_ru")))
        }
        image12?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://kz.usembassy.gov/ru/")))
        }
    }


    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.ABOUT_SCOPE)
        super.onDestroy()
    }
}