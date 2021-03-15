package com.zed.kz.doskaz.main.presentation.auth.choose_language

import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class ChooseLanguagePresenter : BasePresenter<ChooseLanguageFragmentView>(){

    fun onKzBtnClicked(){
        LocalStorage.setLang(AppConstants.LANG_KZ)
        LocalStorage.setLangChoose(true)
        Handler().postDelayed({ viewState?.changeLanguage() }, 500)
    }

    fun onRuBtnClicked(){
        LocalStorage.setLang(AppConstants.LANG_RU)
        LocalStorage.setLangChoose(true)
        Handler().postDelayed({ viewState?.changeLanguage() }, 500)
    }

}