package com.zed.kz.doskaz.main.presentation.main.objects.info.category

import com.arellomobile.mvp.InjectViewState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.Category
import com.zed.kz.doskaz.entity.form.AddObjectGroup
import com.zed.kz.doskaz.entity.form.AddObjectSubForm
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.entity.object_info.Zones
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class ObjectInfoCategoryPresenter(
    private val resourceManager: ResourceManager,
    private val listInteractor: ListInteractor,
    private val objectInteractor: ObjectInteractor
): BasePresenter<ObjectInfoCategoryFragmentView>(){

    private var zones: Zones? = null
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
        zones = LocalStorage.getCurrentObjectItem().attributes?.zones
        categoryList.add(Category(title = resourceManager.getString(R.string.om_parking), type = AppConstants.ATTR_TYPE_PARKING))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_in_group), type = AppConstants.ATTR_TYPE_ENTRANCE))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_rout), type = AppConstants.ATTR_TYPE_MOVEMENT))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_service_zone), type = AppConstants.ATTR_TYPE_SERVICE))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_toilet), type = AppConstants.ATTR_TYPE_TOILET))
        categoryList.add(Category(title = resourceManager.getString(R.string.object_navigation), type = AppConstants.ATTR_TYPE_NAVIGATION))
        categoryList.add(Category(title = resourceManager.getString(R.string.om_serviceAccessibility), type = AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY))
        categoryList.add(Category(title = resourceManager.getString(R.string.om_kidsAccessibility), type = AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY))

        getObject()
    }

    private fun getObject(){
        viewState?.showProgressBar(true)
        LocalStorage.getCurrentObjectItem().id?.let {
            objectInteractor.getObjectById(it)
                .subscribe(
                    {
                        it.attributes?.form?.let { it1 -> getAttributes(it1) }
                    },
                    {
                        it.printStackTrace()
                    }
                ).connect()
        }

    }

    private fun getAttributes(type: String){
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
                    when (type) {
                        AppConstants.FORM_TYPE_SMALL -> {
                            it.small?.let { initAddObjectSubForm(it) }
                        }
                        AppConstants.FORM_TYPE_MIDDLE -> {
                            it.middle?.let { initAddObjectSubForm(it) }
                        }
                        else -> {
                            it.full?.let { initAddObjectSubForm(it) }
                        }
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
        addObjectGroup.subGroups?.forEach {
            it.attributes?.forEach {
                dataList.add(
                    AddObject(
                        title = "${it.title ?: ""}${if (it.subTitle == null) "" else "\n${it.subTitle}"}",
                        key = it.key,
                        value = AppConstants.OBJECT_ATTR_UNKNOWN
                    )
                )
            }
        }
    }


    fun onCategoryItemSelected(position: Int){
        when(position){
            0 -> {
                setAttributeValue(parkingList, zones?.parking)
                viewState.openInfoDetailsFragment(parkingList)
            }
            1 -> {
                setAttributeValue(entranceList, zones?.entrance1)
                viewState.openInfoDetailsFragment(entranceList)
            }
            2 -> {
                setAttributeValue(movementList, zones?.movement)
                viewState.openInfoDetailsFragment(movementList)
            }
            3 -> {
                setAttributeValue(serviceList, zones?.service)
                viewState.openInfoDetailsFragment(serviceList)
            }
            4 -> {
                setAttributeValue(toiletList, zones?.toilet)
                viewState.openInfoDetailsFragment(toiletList)
            }
            5 -> {
                setAttributeValue(navigationList, zones?.navigation)
                viewState.openInfoDetailsFragment(navigationList)
            }
            6 -> {
                setAttributeValue(serviceAccessibilityList, zones?.serviceAccessibility)
                viewState.openInfoDetailsFragment(serviceAccessibilityList)
            }
            7 -> {
                setAttributeValue(kidsAccessibilityList, zones?.kidsAccessibility)
                viewState.openInfoDetailsFragment(kidsAccessibilityList)
            }
        }
    }

    private fun setAttributeValue(dataList: List<AddObject>, zone: JsonObject?){
        val zoneMap: MutableMap<String, String> = mutableMapOf()
        zone?.keySet()?.forEach {
            Timber.i("KEY=$it, VAL=${zone.get(it).asString}")
            zoneMap[it.replace("attribute", "")] = zone.get(it).asString }

        dataList.forEach {
            val value = zoneMap[it.key.toString()]
            it.value = if (!value.isNullOrEmpty()) value else AppConstants.OBJECT_ATTR_UNKNOWN
        }
    }

}