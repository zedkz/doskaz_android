package com.zed.kz.doskaz.main.presentation.auth.sign_in

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.bonus.BonusFragment
import com.zed.kz.doskaz.main.presentation.auth.sms.SmsFragment
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import com.zed.kz.doskaz.main.presentation.web_view.WebViewFragment
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import ru.mail.auth.sdk.MailRuAuthSdk
import ru.mail.auth.sdk.MailRuCallback
import ru.mail.auth.sdk.api.token.OAuthTokensResult
import timber.log.Timber


class SignInFragment : BaseFragment(), SignInFragmentView{

    companion object{

        val TAG = "SignInFragment"

        private val GOOGLE_SIGN_IN = 1
        private val MAIL_SIGN_IN = 2

        fun newInstance(): SignInFragment =
            SignInFragment()
    }

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter(): SignInPresenter {
        getKoin().getScopeOrNull(MainScope.SIGN_IN_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.SIGN_IN_SCOPE, named(MainScope.SIGN_IN_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_sign_in

    private var googSignInClient: GoogleSignInClient? = null
    private var callbackManager: CallbackManager? = null

    override fun setUp(savedInstanceState: Bundle?) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()

        googSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }

        btnNextSignIn.setOnClickListener {
            activity?.let { it1 -> presenter.onNextBtnClicked(edtPhoneSignIn.rawText, it1) }
        }

        imgGoogleSignIn.setOnClickListener {
            this.startActivityForResult(googSignInClient?.signInIntent, GOOGLE_SIGN_IN)
        }

        imgVkSignIn.setOnClickListener {
            activity?.let { it1 -> presenter.onVkAuthClicked(it1) }
        }

        imgFbSignIn.setOnClickListener {
            LoginManager.getInstance().logOut()
            LoginManager.getInstance().loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK
            LoginManager.getInstance().logIn(this, listOf("email"))
        }

        imgMailSignIn.setOnClickListener {
            MailRuAuthSdk.getInstance().setRequestCodeOffset(MAIL_SIGN_IN)
            MailRuAuthSdk.getInstance().startLogin(this)
        }

        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    vkReceiver,
                    IntentFilter(AppConstants.INTENT_FILTER_VK)
                )
        }

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    result?.accessToken?.token?.let { presenter.onSuccessFacebookAuth(it) }
                }

                override fun onCancel() {

                }

                override fun onError(error: FacebookException?) {

                }
            }
        )

        rootSignIn?.setOnClickListener {  }
    }

    override fun openSmsFragment(
        phone: String,
        storeVerificationId: String,
        resentToken: PhoneAuthProvider.ForceResendingToken
    ) {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            SmsFragment.newInstance(phone, storeVerificationId, resentToken),
            SmsFragment.TAG
        )
    }

    override fun openBonusFragment() {
        activity?.replaceFragment(
            R.id.container,
            BonusFragment.newInstance(),
            BonusFragment.TAG
        )
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(vkReceiver)
        }
        getKoin().getScopeOrNull(MainScope.SIGN_IN_SCOPE)
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.i("DSDS=aaqq")
        if (requestCode == GOOGLE_SIGN_IN){
            presenter.googleAuth(data)
        }
        MailRuAuthSdk.getInstance().handleAuthResult(
            requestCode,
            resultCode,
            data,
            SDKResultCallback()
        )
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val vkReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val token = intent?.extras?.getString(AppConstants.TOKEN)
            Timber.i("VKK=${token}")
            token?.let { presenter.onSuccessVkAuth(it) }
        }
    }

    inner class SDKResultCallback : MailRuCallback<OAuthTokensResult, Int> {
        override fun onResult(result: OAuthTokensResult) {
            Timber.i("ASDASD=${result.accessToken}")
            presenter.onSuccessMailruAuth(result.accessToken)
        }

        override fun onError(error: Int) {

        }
    }

    override fun openMainFragment() {
        activity?.replaceFragment(
            R.id.container,
            HomeFragment.newInstance(),
            HomeFragment.TAG
        )
    }
}