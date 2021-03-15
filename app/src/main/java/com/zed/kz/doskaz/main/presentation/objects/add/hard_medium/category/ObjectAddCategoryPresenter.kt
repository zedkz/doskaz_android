package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.category

import com.arellomobile.mvp.InjectViewState
import com.google.gson.Gson
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.entity.form.AddObjectGroup
import com.zed.kz.doskaz.entity.form.AddObjectSubForm
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class ObjectAddCategoryPresenter(
    private val type: String,
    private val resourceManager: ResourceManager,
    private val listInteractor: ListInteractor,
    private val objectInteractor: ObjectInteractor
): BasePresenter<ObjectAddCategoryFragmentView>(){

    private var createObject: CreateObjectMediumHard? = null
    private val categoryList: MutableList<Category> = mutableListOf()
    private val parkingList: MutableList<AddObject> = mutableListOf()
    private val entranceList: MutableList<AddObject> = mutableListOf()
    private val movementList: MutableList<AddObject> = mutableListOf()
    private val serviceList: MutableList<AddObject> = mutableListOf()
    private val toiletList: MutableList<AddObject> = mutableListOf()
    private val navigationList: MutableList<AddObject> = mutableListOf()
    private val serviceAccessibilityList: MutableList<AddObject> = mutableListOf()
    private val kidsAccessibilityList: MutableList<AddObject> = mutableListOf()

    fun onFirstInit(){
        if(type == AppConstants.FORM_TYPE_MIDDLE) {
            viewState?.showFormTitle(resourceManager.getString(R.string.object_middle))
            createObject = CreateObjectMediumHard.newInstance(AppConstants.FORM_TYPE_MIDDLE)
        }
        else if (type == AppConstants.FORM_TYPE_FULL) {
            viewState?.showFormTitle(resourceManager.getString(R.string.object_full))
            createObject = CreateObjectMediumHard.newInstance(AppConstants.FORM_TYPE_FULL)
        }

        categoryList.add(Category(title = resourceManager.getString(R.string.object_common_info), type = AppConstants.ATTR_TYPE_COMMON_INFO))
        categoryList.add(Category(title = resourceManager.getString(R.string.om_parking), type = AppConstants.ATTR_TYPE_PARKING))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_in_group), type = AppConstants.ATTR_TYPE_ENTRANCE))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_rout), type = AppConstants.ATTR_TYPE_MOVEMENT))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_service_zone), type = AppConstants.ATTR_TYPE_SERVICE))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_toilet), type = AppConstants.ATTR_TYPE_TOILET))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_navigation), type = AppConstants.ATTR_TYPE_NAVIGATION))
        categoryList.add(Category(title = resourceManager.getString(R.string.om_serviceAccessibility), type = AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY))
        categoryList.add(Category(title = resourceManager.getString(R.string.om_kidsAccessibility), type = AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY))

        getAttributes()
    }

    private fun getAttributes(){
        viewState?.showProgressBar(true)
        parkingList.clear()
        entranceList.clear()
        movementList.clear()
        serviceList.clear()
        toiletList.clear()
        navigationList.clear()
        serviceAccessibilityList.clear()
        kidsAccessibilityList.clear()
        listInteractor.getAttributes()
            .subscribe(
                {
                    if (type == AppConstants.FORM_TYPE_MIDDLE)
                        it.middle?.let { initAddObjectSubForm(it) }
                    else if (type == AppConstants.FORM_TYPE_FULL){
                        it.full?.let { initAddObjectSubForm(it) }
                    }
                    viewState?.showProgressBar(false)
                    viewState?.showCategoryData(categoryList)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun initAddObjectSubForm(addObjectSubForm: AddObjectSubForm){
        addObjectSubForm.parking?.forEach { initAddObjectGroup(it, parkingList) }
        addObjectSubForm.entrance?.forEach { initAddObjectGroup(it, entranceList) }
        addObjectSubForm.movement?.forEach { initAddObjectGroup(it, movementList) }
        addObjectSubForm.service?.forEach { initAddObjectGroup(it, serviceList) }
        addObjectSubForm.toilet?.forEach { initAddObjectGroup(it, toiletList) }
        addObjectSubForm.navigation?.forEach { initAddObjectGroup(it, navigationList) }
        addObjectSubForm.serviceAccessibility?.forEach { initAddObjectGroup(it, serviceAccessibilityList) }
        addObjectSubForm.kidsAccessibility?.forEach { initAddObjectGroup(it, kidsAccessibilityList) }
    }

    private fun initAddObjectGroup(addObjectGroup: AddObjectGroup, dataList: MutableList<AddObject>){
        addObjectGroup.title?.let {
            dataList.add(
                AddObject(
                    title = it,
                    type = AddObject.TYPE_HEADER
                )
            )
        }
        addObjectGroup.subGroups?.forEach {
            it.title?.let {
                dataList.add(
                    AddObject(
                        title = it,
                        type = AddObject.TYPE_HEADER_SMALL
                    )
                )
            }
            it.attributes?.forEach {
                dataList.add(
                    AddObject(
                        title = "${it.title ?: ""}${if (it.subTitle == null) "" else "\n${it.subTitle}"}",
                        type = AddObject.TYPE_CONTENT,
                        value = resourceManager.getString(R.string.unknown),
                        valueToUpload = AppConstants.OBJECT_ATTR_UNKNOWN,
                        key = it.key
                    )
                )
            }
        }
    }


    fun onCategoryItemSelected(position: Int){
        when(position){
            0 -> { viewState.openAddObjectCommonFragment() }
            1 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_PARKING, parkingList) }
            2 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_ENTRANCE, entranceList) }
            3 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_MOVEMENT, movementList) }
            4 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_SERVICE, serviceList) }
            5 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_TOILET, toiletList) }
            6 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_NAVIGATION, navigationList) }
            7 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY, serviceAccessibilityList) }
            8 -> { viewState.openAddObjectDynamicFragment(AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY, kidsAccessibilityList) }
        }
    }

    fun onDynamicFragmentResult(){
        val instance = CreateObjectMediumHard.getInstance()
        categoryList.forEach {
            when(it.type){
                AppConstants.ATTR_TYPE_PARKING -> { it.isSelected = instance?.parking != null }
                AppConstants.ATTR_TYPE_ENTRANCE -> { it.isSelected = instance?.entrance1 != null }
                AppConstants.ATTR_TYPE_MOVEMENT -> { it.isSelected = instance?.movement != null }
                AppConstants.ATTR_TYPE_SERVICE -> { it.isSelected = instance?.service != null }
                AppConstants.ATTR_TYPE_TOILET -> { it.isSelected = instance?.toilet != null }
                AppConstants.ATTR_TYPE_NAVIGATION -> { it.isSelected = instance?.navigation != null }
                AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY -> { it.isSelected = instance?.serviceAccessibility != null }
                AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY -> { it.isSelected = instance?.kidsAccessibility != null }
            }
        }
        viewState.showCategoryData(categoryList)
    }

    fun onCommonFragmentResult(){
        categoryList.filter { it.type == AppConstants.ATTR_TYPE_COMMON_INFO }
            .forEach { it.isSelected = !createObject?.first?.name.isNullOrEmpty() }
        viewState.showCategoryData(categoryList)
    }

    fun onReadyBtnClicked(){

        if (createObject?.first?.name.isNullOrEmpty() ||
                createObject?.parking == null ||
                createObject?.entrance1 == null ||
                createObject?.movement == null ||
                createObject?.service == null ||
                createObject?.toilet == null ||
                createObject?.navigation == null ||
                createObject?.serviceAccessibility == null ||
                createObject?.kidsAccessibility == null){
            viewState?.showMessage(resourceManager.getString(R.string.object_fill_all_form))
            return
        }

        createObject?.let { createObjectValidate(it) }
    }

    private fun createObjectValidate(createObjectMediumHard: CreateObjectMediumHard){
        viewState?.showProgressBar(true)
        objectInteractor.createObjectValidate(createObjectMediumHard)
            .subscribe(
                {
                    createObject(createObjectMediumHard)
                },
                {
                    viewState?.showMessage(it.getObjectErrorMessage(resourceManager))
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    private fun createObject(createObjectMediumHard: CreateObjectMediumHard){
        objectInteractor.createObject(createObjectMediumHard)
            .subscribe(
                {
                    viewState?.apply {
                        closeThisFragment()
                        showProgressBar(false)
                    }
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

}