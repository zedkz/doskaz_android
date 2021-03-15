package com.zed.kz.doskaz.main.presentation.main.settings

import android.content.DialogInterface
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.app.DoskazApp
import com.zed.kz.doskaz.entity.City
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.Settings
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import timber.log.Timber

@InjectViewState
class SettingsPresenter(
    private val resourceManager: ResourceManager,
    private val listInteractor: ListInteractor
): BasePresenter<SettingsFragmentView>(){

    private var cityList: List<City> = listOf()

    fun onFirstInit(){
        viewState.showData(
            listOf(
                Settings(resourceManager.getString(R.string.settings_language)),
                Settings(resourceManager.getString(R.string.settings_notification)),
                Settings(resourceManager.getString(R.string.settings_category)),
                Settings(resourceManager.getString(R.string.settings_city))
            )
        )
    }

    fun onSettingsItemSelected(position: Int){
        when(position){
            0 -> { onLangBtnClicked() }
            1 -> { }
            2 -> { viewState?.openDisabilityCategoryFragment() }
            3 -> { onCityBtnClicked() }
        }
    }

    private fun onCityBtnClicked(){
        viewState?.showProgressBar(true)
        listInteractor.getCities()
            .subscribe(
                {
                    cityList = it
                    val tempList: MutableList<ListItem> = mutableListOf()
                    it.forEach {
                        tempList.add(
                            ListItem(
                                id = it.id,
                                title = it.name,
                                requestCode = AppConstants.RC_CITY
                            )
                        )
                    }
                    viewState?.openListDialogFragment(tempList)
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onListItemSelected(listItem: ListItem){
        when(listItem.requestCode){
            AppConstants.RC_CITY -> {
                cityList.forEach {
                    if (it.id == listItem.id){
                        LocalStorage.setCurrentCity(it)
                    }
                }
            }
        }
    }

    private fun onLangBtnClicked(){
        val langList: ArrayList<String> = arrayListOf()
        langList.add(resourceManager.getString(R.string.lang_kz))
        langList.add(resourceManager.getString(R.string.lang_ru))

        viewState?.openLangDialogFragment(langList)
    }

    fun onLangItemSelected(dialog: DialogInterface, position: Int){
        when(position){
            0 -> LocalStorage.setLang(AppConstants.LANG_KZ)
            1 -> LocalStorage.setLang(AppConstants.LANG_RU)
        }
        dialog.dismiss()
        Handler().postDelayed({ viewState?.changeLanguage() }, 500)
    }

}