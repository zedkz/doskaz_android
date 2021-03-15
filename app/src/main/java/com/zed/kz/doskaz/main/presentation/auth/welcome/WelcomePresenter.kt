package com.zed.kz.doskaz.main.presentation.auth.welcome

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.WelcomeItem
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.LocalStorage

@InjectViewState
class WelcomePresenter(
    private val resourceManager: ResourceManager
): BasePresenter<WelcomeFragmentView>(){

    private var viewPagerPosition: Int = 0
    private val dataList: MutableList<WelcomeItem> = mutableListOf()

    fun onFirstInit(){
        initViewPagerData()
        viewState?.showViewPagerData(dataList)
    }

    fun onPageSelected(position: Int){
        viewPagerPosition = position
    }

    private fun initViewPagerData(){
        dataList.add(
            WelcomeItem(
                title = resourceManager.getString(R.string.welcome_title_1),
                text = resourceManager.getString(R.string.welcome_text_1),
                imageResourceId = R.drawable.ic_4
            )
        )
        dataList.add(
            WelcomeItem(
                title = resourceManager.getString(R.string.welcome_title_2),
                text = resourceManager.getString(R.string.welcome_text_2),
                imageResourceId = R.drawable.ic_2
            )
        )
        dataList.add(
            WelcomeItem(
                title = resourceManager.getString(R.string.welcome_title_3),
                text = resourceManager.getString(R.string.welcome_text_3),
                imageResourceId = R.drawable.ic_3
            )
        )
    }

    fun onNextBtnClicked(){
        if (viewPagerPosition < dataList.size){
            viewPagerPosition++
            viewState?.setViewPagerPosition(viewPagerPosition)
        }else{
            LocalStorage.setFirstTimeLaunched(false)
            viewState?.openDisabilityCategoryFragment()
        }
    }

    fun onPreviousBtnClicked(){
        if (viewPagerPosition >= 0){
            viewPagerPosition--
            viewState?.setViewPagerPosition(viewPagerPosition)
        }
    }

}