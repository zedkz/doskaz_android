package com.zed.kz.doskaz.main.presentation.objects.add.simple

import com.arellomobile.mvp.InjectViewState
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.form.AddObjectGroup
import com.zed.kz.doskaz.entity.form.AddObjectSubForm
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.AppConstants.RC_ADD_OBJECT
import com.zed.kz.doskaz.global.utils.AppConstants.RC_CATEGORY
import com.zed.kz.doskaz.global.utils.AppConstants.RC_SUB_CATEGORY
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class AddSimpleObjectPresenter(
    private val listInteractor: ListInteractor,
    private val objectInteractor: ObjectInteractor,
    private val resourceManager: ResourceManager
): BasePresenter<AddSimpleObjectFragmentView>(){

    private val videoDataList: MutableList<String> = mutableListOf()
    private val photoDataList: MutableList<String> = mutableListOf()
    private var defaultAnswerList: MutableList<AnswerItem> = mutableListOf()
    private var currentSelectedCategory: Category? = null

    private var createObject: CreateObjectMediumHard? = null
    private var simpleObjectItemsList: MutableList<SimpleObjectItem> = mutableListOf()
    private var categoryList: List<Category> = listOf()
    private val parkingList: MutableList<AddObject> = mutableListOf()
    private val entranceList: MutableList<AddObject> = mutableListOf()
    private val movementList: MutableList<AddObject> = mutableListOf()
    private val serviceList: MutableList<AddObject> = mutableListOf()
    private val toiletList: MutableList<AddObject> = mutableListOf()
    private val navigationList: MutableList<AddObject> = mutableListOf()
    private val serviceAccessibilityList: MutableList<AddObject> = mutableListOf()
    private val kidsAccessibility: MutableList<AddObject> = mutableListOf()
    private var zoneScoreCounter = 8
    private var currentAddObject: AddObject? = null
    private var currentSimpleObjectItem: SimpleObjectItem? = null

    fun onFirstInit(){
        createObject = CreateObjectMediumHard(form = AppConstants.FORM_TYPE_SMALL)
        getAttributes()

        videoDataList.add("")
        viewState?.showVideoData(videoDataList)

        photoDataList.add("")
        viewState?.showPhotoData(photoDataList)

        getCategories()
        initDefaultAnswerList()
    }

    private fun initDefaultAnswerList(){
        defaultAnswerList.addAll(
            listOf(
                AnswerItem(
                    title = resourceManager.getString(R.string.yes),
                    type = AppConstants.OBJECT_ATTR_YES
                ),
                AnswerItem(
                    title = resourceManager.getString(R.string.no),
                    type = AppConstants.OBJECT_ATTR_NO
                ),
                AnswerItem(
                    title = resourceManager.getString(R.string.not_provided),
                    type = AppConstants.OBJECT_ATTR_NOT_PROVIDED
                ),
                AnswerItem(
                    title = resourceManager.getString(R.string.unknown),
                    type = AppConstants.OBJECT_ATTR_UNKNOWN
                )
            )
        )
    }

    fun onAddVideoItemClicked(){
        videoDataList.add("")
        viewState?.showVideoData(videoDataList)
    }

    fun onRemoveVideoItemClicked(position: Int){
        videoDataList.removeAt(position)
        viewState?.showVideoData(videoDataList)
    }

    fun addPickedImage(paths: List<String>){
        paths.forEach {
            photoDataList.add(it)
            viewState?.showPhotoData(photoDataList)
            uploadPhoto(it)
        }
    }

    fun onMapBtnClicked(){
        viewState?.openMapDialogFragment()
    }

    private fun getCategories(){
        listInteractor.getCategories()
            .subscribe(
                {
                    categoryList = it
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onCategoryBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        categoryList.forEach {
            dataList.add(
                ListItem(
                    id = it.id,
                    title = it.title,
                    requestCode = RC_CATEGORY
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onSubCategoryBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        categoryList.forEach {
            if (it.id == currentSelectedCategory?.id){
                it.subCategories?.forEach {
                    dataList.add(
                        ListItem(
                            id = it.id,
                            title = it.title,
                            requestCode = RC_SUB_CATEGORY
                        )
                    )
                }
            }
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onMapPositionSelected(latLng: LatLng, address: String){
        createObject?.first?.point = mutableListOf()
        createObject?.first?.point?.let {
            it.add(latLng.latitude)
            it.add(latLng.longitude)
        }
        viewState?.showMapLatLngAndAddress(latLng, address)
    }

    private fun uploadPhoto(path: String?){
        viewState?.showProgressBar(true)
        objectInteractor.upload(path)
            .subscribe(
                {
                    if (createObject?.first == null) createObject?.first = First()
                    createObject?.first?.photos?.add(it.path ?: "")
                    viewState?.showProgressBar(false)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onReadyBtnClicked(
        name: String,
        otherName: String,
        description: String,
        address: String
    ){
        if (createObject?.first == null) createObject?.first = First()

        createObject?.first?.let {
            it.name = name
            it.otherNames = otherName
            it.description = description
            it.address = address
            it.videos = videoDataList.filter { it.isNotEmpty() }
        }

        createObject?.let { createObjectValidate(it) }
    }

    private fun createObjectValidate(createObjectMediumHard: CreateObjectMediumHard){
        simpleObjectItemsList.forEach {
            initForm(
                simpleObjectItem = it,
                isOnlyDataInit = true
            )
        }
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

    fun onListItemSelected(listItem: ListItem) {
        val title = listItem.title ?: ""
        when (listItem.requestCode) {
            RC_CATEGORY -> {
                categoryList.forEach { if (it.id == listItem.id) currentSelectedCategory = it }
                viewState?.showCategoryTitle(title)
            }
            RC_SUB_CATEGORY -> {
                if (createObject?.first == null) createObject?.first = First()
                createObject?.first?.categoryId = listItem.id
                viewState?.showSubCategoryTitle(title)
            }
            RC_ADD_OBJECT -> {
                viewState?.showProgressBar(true)
                currentAddObject?.value = title
                currentAddObject?.valueToUpload = listItem.type
                currentSimpleObjectItem?.let { initForm(it, true) }
            }
        }
    }

    private fun getAttributes(){
        viewState?.showProgressBar(true)
        simpleObjectItemsList.clear()
        parkingList.clear()
        entranceList.clear()
        movementList.clear()
        serviceList.clear()
        toiletList.clear()
        navigationList.clear()
        serviceAccessibilityList.clear()
        kidsAccessibility.clear()
        listInteractor.getAttributes()
            .subscribe(
                {
                    it.small?.let { initAddObjectSubForm(it) }

                    val parkingObject = SimpleObjectItem(
                        addObjectList = parkingList,
                        type =  AppConstants.ATTR_TYPE_PARKING
                    )
                    simpleObjectItemsList.add(parkingObject)
                    initForm(parkingObject)

                    val entranceObject = SimpleObjectItem(
                        addObjectList = entranceList,
                        type =  AppConstants.ATTR_TYPE_ENTRANCE
                    )
                    simpleObjectItemsList.add(entranceObject)
                    initForm(entranceObject)

                    val movementObject = SimpleObjectItem(
                        addObjectList = movementList,
                        type =  AppConstants.ATTR_TYPE_MOVEMENT
                    )
                    simpleObjectItemsList.add(movementObject)
                    initForm(movementObject)

                    val serviesObject = SimpleObjectItem(
                        addObjectList = serviceList,
                        type =  AppConstants.ATTR_TYPE_SERVICE
                    )
                    simpleObjectItemsList.add(serviesObject)
                    initForm(serviesObject)

                    val toiletObject = SimpleObjectItem(
                        addObjectList = toiletList,
                        type =  AppConstants.ATTR_TYPE_TOILET
                    )
                    simpleObjectItemsList.add(toiletObject)
                    initForm(toiletObject)

                    val navigationObject = SimpleObjectItem(
                        addObjectList = navigationList,
                        type =  AppConstants.ATTR_TYPE_NAVIGATION
                    )
                    simpleObjectItemsList.add(navigationObject)
                    initForm(navigationObject)

                    val serviceAccessibilityObject = SimpleObjectItem(
                        addObjectList = serviceAccessibilityList,
                        type =  AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY
                    )
                    simpleObjectItemsList.add(serviceAccessibilityObject)
                    initForm(serviceAccessibilityObject)

                    val kidsAccessibilityObject = SimpleObjectItem(
                        addObjectList = kidsAccessibility,
                        type =  AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY
                    )
                    simpleObjectItemsList.add(kidsAccessibilityObject)
                    initForm(kidsAccessibilityObject)
                },
                {
                    it.printStackTrace()
                    viewState?.showProgressBar(false)
                }
            ).connect()
    }

    private fun initAddObjectSubForm(addObjectSubForm: AddObjectSubForm){
        addObjectSubForm.parking?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_parking), it, parkingList) }
        addObjectSubForm.entrance?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_entrance), it, entranceList) }
        addObjectSubForm.movement?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_movement), it, movementList) }
        addObjectSubForm.service?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_service), it, serviceList) }
        addObjectSubForm.toilet?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_toilet), it, toiletList) }
        addObjectSubForm.navigation?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_navigation), it, navigationList) }
        addObjectSubForm.serviceAccessibility?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_serviceAccessibility), it, serviceAccessibilityList) }
        addObjectSubForm.kidsAccessibility?.forEach { initAddObjectGroup(resourceManager.getString(R.string.om_kidsAccessibility), it, kidsAccessibility) }
    }

    private fun initAddObjectGroup(groupTitle: String, addObjectGroup: AddObjectGroup, dataList: MutableList<AddObject>){
        dataList.add(
            AddObject(
                title = groupTitle,
                type = AddObject.TYPE_HEADER
            )
        )
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

    private fun initForm(simpleObjectItem: SimpleObjectItem, forSingleObject: Boolean = false, isOnlyDataInit: Boolean = false) {
        val attributesObject = JsonObject()
        val attributeObject = JsonObject()
        simpleObjectItem.addObjectList.forEach { attributeObject.addProperty("attribute${it.key}", it.valueToUpload) }
        attributesObject.add("attributes", attributeObject)
        if (!simpleObjectItem.comment.isNullOrEmpty()) attributesObject.addProperty("comment", simpleObjectItem.comment)
        when(simpleObjectItem.type){
            AppConstants.ATTR_TYPE_PARKING -> createObject?.parking = attributesObject
            AppConstants.ATTR_TYPE_ENTRANCE -> createObject?.entrance1 = attributesObject
            AppConstants.ATTR_TYPE_MOVEMENT -> createObject?.movement = attributesObject
            AppConstants.ATTR_TYPE_SERVICE -> createObject?.service = attributesObject
            AppConstants.ATTR_TYPE_TOILET -> createObject?.toilet = attributesObject
            AppConstants.ATTR_TYPE_NAVIGATION -> createObject?.navigation = attributesObject
            AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY -> createObject?.serviceAccessibility = attributesObject
            AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY -> createObject?.kidsAccessibility = attributesObject
        }

        if (!isOnlyDataInit) calculateAvailabilityZone(simpleObjectItem, attributesObject, forSingleObject)
    }

    private fun calculateAvailabilityZone(simpleObjectItem: SimpleObjectItem, jsonObject: JsonObject, forSingleObject: Boolean){
        Timber.i("ZONEE===${simpleObjectItem.type.replace("1", "")}_${AppConstants.FORM_TYPE_SMALL}")
        jsonObject.addProperty("type", "${simpleObjectItem.type.replace("1", "")}_${AppConstants.FORM_TYPE_SMALL}")
        objectInteractor.calculateAvailability(jsonObject)
            .subscribe(
                {
                    simpleObjectItem.availabilityZone = it
                    zoneScoreCounter -= 1
                    if (!forSingleObject){
                        if (zoneScoreCounter == 0){
                            viewState?.showSimpleDataList(simpleObjectItemsList)
                        }
                    }else{
                        viewState?.showSimpleDataList(simpleObjectItemsList)
                    }
                    viewState?.showProgressBar(false)
                },
                {
                    zoneScoreCounter -= 1
                    if (!forSingleObject){
                        if (zoneScoreCounter == 0){
                            viewState?.showSimpleDataList(simpleObjectItemsList)
                        }
                    }else{
                        viewState?.showSimpleDataList(simpleObjectItemsList)
                    }
                    viewState?.showProgressBar(false)
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onSimpleObjectItemSelected(simpleObjectItem: SimpleObjectItem, addObject: AddObject){
        currentAddObject = addObject
        currentSimpleObjectItem = simpleObjectItem
        viewState?.openListDialogFragment(getListItemsFromDefaultAnswers(RC_ADD_OBJECT))
    }

    private fun getListItemsFromDefaultAnswers(requestCode: Int) : MutableList<ListItem>{
        val dataList: MutableList<ListItem> = mutableListOf()
        defaultAnswerList.forEach {
            dataList.add(
                ListItem(
                    id = 0,
                    title = it.title,
                    type = it.type,
                    requestCode = requestCode
                )
            )
        }
        return dataList
    }

}