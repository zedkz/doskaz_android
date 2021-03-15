package com.zed.kz.doskaz.main.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.app.DoskazApp
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.MenuObject
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.bonus.BonusFragment
import com.zed.kz.doskaz.main.presentation.auth.choose_language.ChooseLanguageFragment
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.auth.welcome.WelcomeFragment
import com.zed.kz.doskaz.main.presentation.main.category.DisabilityCategoryFragment
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import com.zed.kz.doskaz.main.presentation.profile.main.show.ShowProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    @ProvidePresenter
    fun providePresenter(): MainActivityPresenter {
        getKoin().getScopeOrNull(MainScope.MAIN_ACTIVITY_SCOPE)?.close()
        return getKoin().createScope(MainScope.MAIN_ACTIVITY_SCOPE, named(MainScope.MAIN_ACTIVITY_SCOPE)).get()
    }

    override fun attachBaseContext(newBase: Context) {
        val newContext: Context?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val displayMetrics =
                newBase?.resources?.displayMetrics
            val configuration =
                newBase?.resources?.configuration
            if (displayMetrics?.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
                // Current density is different from Default Density. Override it
                displayMetrics?.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
                configuration?.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
                newContext = newBase //baseContext.createConfigurationContext(configuration);
            } else {
                // Same density. Just use same context
                newContext = newBase
            }
        } else {
            // Old API. Screen zoom not supported
            newContext = newBase
        }

        super.attachBaseContext(DoskazApp.localeManager?.setLocale(newContext))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onFirstInit()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLongMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgressBar(show: Boolean) {
        progressBarMain.visible(show)
    }

    override fun openChooseLanguageFragment() {
        replaceFragmentWithBackStack(
            R.id.container,
            ChooseLanguageFragment.newInstance(),
            ChooseLanguageFragment.TAG
        )
    }

    override fun openSignInFragment() {
        replaceFragmentWithBackStack(
            R.id.container,
            SignInFragment.newInstance(),
            SignInFragment.TAG
        )
    }

    override fun openHomeFragment() {
        replaceFragment(
            R.id.container,
            HomeFragment.newInstance(),
            HomeFragment.TAG
        )
    }

    override fun openWelcomeFragment() {
        replaceFragment(
            R.id.container,
            WelcomeFragment.newInstance(),
            WelcomeFragment.TAG
        )
    }

    override fun openDisabilityCategoryFragment() {
        replaceFragment(
            R.id.container,
            DisabilityCategoryFragment.newInstance(),
            DisabilityCategoryFragment.TAG
        )
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MAIN_ACTIVITY_SCOPE)?.close()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                LocalBroadcastManager.getInstance(this@MainActivity)
                    .sendBroadcast(
                        Intent(AppConstants.INTENT_FILTER_VK).apply {
                            putExtra(AppConstants.TOKEN, token.accessToken)
                        }
                    )
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showDownloadAlertDialog(docId: Int) {
        val alertDialog =  AlertDialog.Builder(this)
            .setMessage(getString(R.string.complaint_download_message))
            .setPositiveButton(getString(R.string.download))
            { dialog, _ ->
                presenter.startDownload(docId)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.cancel))
            { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    override fun showObjectCreatedAlertDialog() {
        val alertDialog =  AlertDialog.Builder(this)
            .setMessage(getString(R.string.om_created_message))
            .setPositiveButton(getString(R.string.ok))
            { dialog, _ -> dialog.dismiss() }
            .create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    override fun onBackPressed() {
        if (MenuObject.isMenuOpen){
            LocalBroadcastManager.getInstance(this)
                .sendBroadcast(Intent(AppConstants.FILTER_MENU))
        }else{
            super.onBackPressed()
        }
    }
}