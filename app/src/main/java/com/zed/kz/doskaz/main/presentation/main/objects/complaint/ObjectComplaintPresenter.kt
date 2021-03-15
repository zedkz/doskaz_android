package com.zed.kz.doskaz.main.presentation.main.objects.complaint

import com.arellomobile.mvp.InjectViewState
import com.google.gson.Gson
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.global.extension.getObjectErrorMessage
import com.zed.kz.doskaz.global.extension.getValidationErrorMessage
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.AppConstants.COMPLAINT_TYPE_1
import com.zed.kz.doskaz.global.utils.AppConstants.COMPLAINT_TYPE_2
import com.zed.kz.doskaz.global.utils.AppConstants.RC_COMPLAINT_AUTHORITY
import com.zed.kz.doskaz.global.utils.AppConstants.RC_COMPLAINT_CITY_1
import com.zed.kz.doskaz.global.utils.AppConstants.RC_COMPLAINT_CITY_2
import com.zed.kz.doskaz.global.utils.AppConstants.RC_COMPLAINT_TYPE
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.main.data.interactor.ListInteractor
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import timber.log.Timber

@InjectViewState
class ObjectComplaintPresenter(
    private val objectId: Int?,
    private val resourceManager: ResourceManager,
    private val listInteractor: ListInteractor,
    private val objectInteractor: ObjectInteractor
): BasePresenter<ObjectComplaintFragmentView>(){

    private val videoDataList: MutableList<String> = mutableListOf()
    private val photoDataList: MutableList<String> = mutableListOf()
    private var complaintList: List<Option> = listOf()
    private var cityList: List<City> = listOf()
    private var authorityList: List<Authority> = listOf()
    private var createComplaint = CreateComplaint()

    fun onFirstInit(){
        createComplaint.objectId = objectId
        videoDataList.add("")
        viewState?.showVideoData(videoDataList)

        photoDataList.add("")
        viewState?.showPhotoData(photoDataList)

        getComplaintOptions()
        getCities()
        getAuthorities()

        viewState?.showOptions(false)
        createComplaint.content?.type = COMPLAINT_TYPE_1
        viewState?.showTypeTitle(resourceManager.getString(R.string.complaint_type_1))

        if (LocalStorage.getCreateComplaint() != null)
            showSavedComplaint()
    }

    private fun showSavedComplaint(){
        createComplaint = LocalStorage.getCreateComplaint()!!
        objectId?.let { createComplaint.objectId = it }
        viewState?.showCreatedComplaintInfo(createComplaint)
        when(createComplaint.content?.type){
            COMPLAINT_TYPE_1 -> {
                viewState?.showOptions(false)
                viewState?.showTypeTitle(resourceManager.getString(R.string.complaint_type_1))
            }
            COMPLAINT_TYPE_2 -> {
                viewState?.showOptions(true)
                viewState?.showTypeTitle(resourceManager.getString(R.string.complaint_type_2))
            }
        }

    }

    private fun getComplaintOptions(){
        listInteractor.getComplaintOptions()
            .subscribe(
                {
                    this.complaintList = it

                    if (LocalStorage.getCreateComplaint() != null){

                        this.complaintList.forEach { option ->
                            option.options?.forEach { subOption ->
                                val complaintOption = LocalStorage.getCreateComplaint()?.content?.options?.find { it == subOption.key }
                                if (complaintOption != null) subOption.isSelected = true
                            }
                            option.count = option.options?.filter { it.isSelected }?.size
                            if (option.count == 0) option.count = null
                        }
                    }

                    viewState?.showOptionsData(complaintList)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
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
        viewState?.showComplaintProgressBar(true)
        paths.forEach {
            photoDataList.add(it)
            viewState?.showPhotoData(photoDataList)
            uploadPhoto(it)
        }
    }

    private fun uploadPhoto(path: String?){
        objectInteractor.upload(path)
            .subscribe(
                {
                    it.path?.let { it1 -> createComplaint.content?.photos?.add(it1) }
                    viewState?.showComplaintProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showComplaintProgressBar(false)
                }
            ).connect()
    }

    private fun getCities(){
        listInteractor.getCities()
            .subscribe(
                {
                    cityList = it

                    if (LocalStorage.getCreateComplaint() != null){
                        cityList.forEach {
                            if (LocalStorage.getCreateComplaint()?.content?.cityId == it.id){
                                viewState?.showCityTitle(it.name ?: "")
                            }
                            if (LocalStorage.getCreateComplaint()?.complainant?.cityId == it.id){
                                viewState?.showCityComplaintTitle(it.name ?: "")
                            }
                        }
                    }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun getAuthorities(){
        listInteractor.getAuthorities()
            .subscribe(
                {
                    authorityList = it
                    if (LocalStorage.getCreateComplaint() != null){
                        authorityList.forEach {
                            if (LocalStorage.getCreateComplaint()?.authorityId == it.id){
                                viewState?.showAuthorityTitle(it.name ?: "")
                            }
                        }
                    }
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun onCityBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        cityList.forEach {
            dataList.add(
                ListItem(
                    id = it.id,
                    title = it.name,
                    requestCode = RC_COMPLAINT_CITY_1
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onCityComplaintBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        cityList.forEach {
            dataList.add(
                ListItem(
                    id = it.id,
                    title = it.name,
                    requestCode = RC_COMPLAINT_CITY_2
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onAuthorityBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        authorityList.forEach {
            dataList.add(
                ListItem(
                    id = it.id,
                    title = it.name,
                    requestCode = RC_COMPLAINT_AUTHORITY
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onTypeBtnClicked(){
        val dataList: MutableList<ListItem> = mutableListOf()
        dataList.add(
            ListItem(
                type = COMPLAINT_TYPE_1,
                title = resourceManager.getString(R.string.complaint_type_1),
                requestCode = RC_COMPLAINT_TYPE
            )
        )
        dataList.add(
            ListItem(
                type = COMPLAINT_TYPE_2,
                title = resourceManager.getString(R.string.complaint_type_2),
                requestCode = RC_COMPLAINT_TYPE
            )
        )
        viewState?.openListDialogFragment(dataList)
    }

    fun onListItemSelected(listItem: ListItem) {
        val title = listItem.title ?: ""
        when (listItem.requestCode) {
            RC_COMPLAINT_CITY_1 -> {
                createComplaint.content?.cityId = listItem.id
                viewState?.showCityTitle(title)
            }
            RC_COMPLAINT_CITY_2 -> {
                createComplaint.complainant?.cityId = listItem.id
                viewState?.showCityComplaintTitle(title)
            }
            RC_COMPLAINT_AUTHORITY -> {
                createComplaint.authorityId = listItem.id
                viewState?.showAuthorityTitle(title)
            }
            RC_COMPLAINT_TYPE -> {
                when(listItem.type){
                    COMPLAINT_TYPE_1 -> viewState?.showOptions(false)
                    COMPLAINT_TYPE_2 -> viewState?.showOptions(true)
                }
                createComplaint.content?.type = listItem.type
                viewState?.showTypeTitle(title)
            }
        }
    }

    fun onReadyBtnClicked(
        name: String,
        surname: String,
        middleName: String,
        iin: String,
        street: String,
        house: String,
        apartment: String,
        phone: String,
        nameComplaint: String,
        streetComplaint: String,
        houseComplaint: String,
        officeComplaint: String,
        goalComplaint: String,
        comment: String,
        rememberData: Boolean,
        isLifeThread: Boolean
    ){
        if (createComplaint.content?.photos.isNullOrEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.complaint_choose_photo))
            return
        }

        viewState?.showComplaintProgressBar(true)

        createComplaint.complainant?.apply {
            this.firstName = name
            this.lastName = surname
            this.middleName = middleName
            this.iin = iin
            this.street = street
            this.building = house
            this.apartment = apartment
            this.phone = phone
        }

        createComplaint.content?.apply {
            this.objectName = nameComplaint
            this.street = streetComplaint
            this.building = houseComplaint
            this.office = officeComplaint
            this.videos = videoDataList.filter { it.isNotEmpty() }
            this.visitPurpose = goalComplaint

            if (createComplaint.content?.type == COMPLAINT_TYPE_2){
                this.threatToLife = isLifeThread
                if (comment.isNotEmpty()) this.comment = comment
                this.options?.clear()
                complaintList.forEach {
                    it.options?.forEach {
                        if (it.isSelected)
                            it.key?.let { it1 -> this.options?.add(it1) }
                    }
                }
            }
        }

        if (createComplaint.content?.type == COMPLAINT_TYPE_2 &&
            createComplaint.content?.options.isNullOrEmpty() &&
            createComplaint.content?.comment.isNullOrEmpty()){
            viewState?.showMessage(resourceManager.getString(R.string.complaint_options_warning))
            viewState?.showComplaintProgressBar(false)
            return
        }

        createComplaint.rememberPersonalData = rememberData

        if (rememberData)
            LocalStorage.setCreateComplaint(createComplaint)
        else
            LocalStorage.setCreateComplaint(null)

        objectInteractor.createComplaint(createComplaint)
            .subscribe(
                {
                    viewState?.closeThisFragmentWithResult(it.id ?: 0)
                    viewState?.showComplaintProgressBar(false)
                },
                {
                    it.printStackTrace()
                    viewState?.showComplaintProgressBar(false)
                    viewState?.showMessage(it.getValidationErrorMessage())
                }
            ).connect()
    }

    fun onDatePicked(year: Int, month: Int, day: Int){
        val date = "${String.format("%02d", year)}-${String.format("%02d", month)}-${String.format("%02d", day)}"
        viewState?.showDateTitle(date)
        createComplaint.content?.visitedAt = "${date}T00:00:00+00:0"
    }

}