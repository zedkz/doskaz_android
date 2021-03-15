package com.zed.kz.doskaz.main.presentation.auth.sign_in

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.google.firebase.auth.PhoneAuthProvider
import com.zed.kz.doskaz.global.base.BaseMvpView

interface SignInFragmentView : BaseMvpView{

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSmsFragment(phone: String, storeVerificationId: String, resentToken: PhoneAuthProvider.ForceResendingToken)

    fun openBonusFragment()

    fun openMainFragment()
}