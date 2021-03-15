package com.zed.kz.doskaz.main.presentation.auth.sms

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.auth.PhoneAuthProvider
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.bonus.BonusFragment
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.auth.welcome.WelcomeFragment
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_choose_language.*
import kotlinx.android.synthetic.main.fragment_sms.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SmsFragment : BaseFragment(), SmsFragmentView{

    companion object{

        val TAG = "SmsFragment"

        private val BUNDLE_PHONE = "phone"
        private val BUNDLE_VERIFY_ID = "verification_id"
        private val BUNDLE_RESENT_TOKEN = "resent_token"

        fun newInstance(
            phone: String,
            storeVerificationId: String,
            resentToken: PhoneAuthProvider.ForceResendingToken
        ): SmsFragment =
            SmsFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_PHONE, phone)
                    putString(BUNDLE_VERIFY_ID, storeVerificationId)
                    putParcelable(BUNDLE_RESENT_TOKEN, resentToken)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: SmsPresenter

    @ProvidePresenter
    fun providePresenter(): SmsPresenter {
        getKoin().getScopeOrNull(MainScope.SMS_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.SMS_SCOPE, named(MainScope.SMS_SCOPE))
        val phone = arguments?.getString(BUNDLE_PHONE)
        val storeVerificationId = arguments?.getString(BUNDLE_VERIFY_ID)
        val resentToken = arguments?.getParcelable<PhoneAuthProvider.ForceResendingToken>(BUNDLE_RESENT_TOKEN)
        return scope.get { parametersOf(phone, storeVerificationId, resentToken) }
    }

    override val layoutRes: Int get() = R.layout.fragment_sms

    override fun setUp(savedInstanceState: Bundle?) {
        btnSendSms.setOnClickListener {
            presenter.onSendBtnClicked(edtPhoneSms.text.toString())
        }

        txtSendAgainSms.setOnClickListener {
            activity?.let { it1 -> presenter.sentSmsAgain(it1) }
            txtSendAgainSms.visible(false)
        }
    }

    override fun openBonusFragment() {
        activity?.supportFragmentManager?.popBackStackImmediate(SignInFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        activity?.replaceFragment(
            R.id.container,
            BonusFragment.newInstance(),
            BonusFragment.TAG
        )
    }

    override fun openMainFragment() {
        activity?.supportFragmentManager?.popBackStackImmediate(SignInFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        activity?.replaceFragment(
            R.id.container,
            HomeFragment.newInstance(),
            HomeFragment.TAG
        )
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.SMS_SCOPE)
        super.onDestroy()
    }
}