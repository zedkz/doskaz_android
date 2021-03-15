package com.zed.kz.doskaz.main.presentation.main.filter

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.ListInteractor

@InjectViewState
class FilterPresenter(
    private val listInteractor: ListInteractor
): BasePresenter<FilterFragmentView>(){

    private var categoryList: List<Category> = listOf()
    private var currentSelectedCategory: Category? = null

    fun onFirstInit(){
        getCategories()
    }

    private fun getCategories(){
        listInteractor.getCategories()
            .subscribe(
                {
                    categoryList = it
                    viewState?.showCategoriesData(categoryList)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onFilterItemSelected(category: Category){
////        categoryList.forEach {
////            it.subCategories?.forEach {
////                it.isSelected = false
////            }
////        }
//        category.isSelected = true
//        currentSelectedCategory = category
//        viewState?.showCategoriesData(categoryList)
    }

    fun onReadyBtnClicked(isFullAccessible: Boolean, isPartialAccessible: Boolean, isNotAccessible: Boolean){
        val params: MutableMap<String, String> = mutableMapOf()
        when{
            (isFullAccessible && isPartialAccessible && isNotAccessible) -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE
                params["accessibilityLevels[1]"] = AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE
                params["accessibilityLevels[2]"] = AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE
            }
            (isFullAccessible && isPartialAccessible) -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE
                params["accessibilityLevels[1]"] = AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE
            }
            (isFullAccessible && isNotAccessible) -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE
                params["accessibilityLevels[1]"] = AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE
            }
            (isPartialAccessible && isNotAccessible) -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE
                params["accessibilityLevels[1]"] = AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE
            }
            isFullAccessible -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE
            }
            isPartialAccessible -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE
            }
            isNotAccessible -> {
                params["accessibilityLevels[0]"] = AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE
            }
        }
        var counter = 0
        categoryList.forEach {
            it.subCategories?.forEach {
                if (it.isSelected){
                    params["categories[$counter]"] = it.id.toString()
                    counter++
                }
            }
        }

        viewState?.closeThisFragmentWithResult(if (params.isEmpty()) null else params)
    }

}