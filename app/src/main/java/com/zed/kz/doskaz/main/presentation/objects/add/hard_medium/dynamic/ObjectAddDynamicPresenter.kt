package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.dynamic

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.AddObject
import com.zed.kz.doskaz.entity.AnswerItem
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.medium_hard.AttributesMediumHard
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.views.ObjectBigEditTextView
import com.zed.kz.doskaz.main.data.interactor.ObjectInteractor
import kotlinx.android.synthetic.main.item_simple_object.view.*
import timber.log.Timber

@InjectViewState
class ObjectAddDynamicPresenter(
    private val type: String,
    private val dataList: List<AddObject>,
    private val context: Context,
    private val resourceManager: ResourceManager,
    private val objectInteractor: ObjectInteractor
): BasePresenter<ObjectAddDynamicFragmentView>(){

    private var defaultAnswerList: MutableList<AnswerItem> = mutableListOf()
    private var currentSelectedContentItem: AddObject? = null

    fun onFirstInit(){
        viewState?.showUIData(dataList)
        initDefaultAnswerList()
        initForm("")
        when(type){
            AppConstants.ATTR_TYPE_ENTRANCE -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_1)))
            }
            AppConstants.ATTR_TYPE_PARKING -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_2)))
            }
            AppConstants.ATTR_TYPE_SERVICE -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_3)))
            }
            AppConstants.ATTR_TYPE_TOILET -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_4)))
            }
            AppConstants.ATTR_TYPE_NAVIGATION -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_5)))
            }
            AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_6)))
            }
            AppConstants.ATTR_TYPE_MOVEMENT -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_7)))
            }
            AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY -> {
                viewState?.showAvailabilityTitle(context.getString(R.string.availability_disagree, context.getString(R.string.mark_8)))
            }
        }
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

    fun onContentItemSelected(addObject: AddObject){
        val dataList: MutableList<ListItem> = mutableListOf()
        currentSelectedContentItem = addObject
        defaultAnswerList.forEach {
            dataList.add(
                ListItem(
                    id = 0,
                    title = it.title,
                    type = it.type,
                    requestCode = 0
                )
            )
        }
        viewState?.openListDialogFragment(dataList)
    }

    fun onListItemSelected(listItem: ListItem){
        currentSelectedContentItem?.value = listItem.title
        currentSelectedContentItem?.valueToUpload = listItem.type
        viewState?.showUIData(dataList)
        initForm("")
    }

    fun onReadyBtnClicked(comment: String){
        initForm(comment)

        viewState.closeThisFragmentWithResult()
    }

    private fun initForm(comment: String){
        val attributesObject = JsonObject()
        val attributeObject = JsonObject()
        dataList.forEach { attributeObject.addProperty("attribute${it.key}", it.valueToUpload) }
        attributesObject.add("attributes", attributeObject)
        if (comment.isNotEmpty()) attributesObject.addProperty("comment", comment)

        when(type){
            AppConstants.ATTR_TYPE_PARKING -> {
                CreateObjectMediumHard.getInstance()?.parking = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_parking))
            }
            AppConstants.ATTR_TYPE_ENTRANCE -> {
                CreateObjectMediumHard.getInstance()?.entrance1 = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_entrance))
            }
            AppConstants.ATTR_TYPE_MOVEMENT -> {
                CreateObjectMediumHard.getInstance()?.movement = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_movement))
            }
            AppConstants.ATTR_TYPE_SERVICE -> {
                CreateObjectMediumHard.getInstance()?.service = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_service))
            }
            AppConstants.ATTR_TYPE_TOILET -> {
                CreateObjectMediumHard.getInstance()?.toilet = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_toilet))
            }
            AppConstants.ATTR_TYPE_NAVIGATION -> {
                CreateObjectMediumHard.getInstance()?.navigation = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_navigation))
            }
            AppConstants.ATTR_TYPE_SERVICE_ACCESSIBILITY -> {
                CreateObjectMediumHard.getInstance()?.serviceAccessibility = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_serviceAccessibility))
            }
            AppConstants.ATTR_TYPE_KIDS_ACCESSIBILITY -> {
                CreateObjectMediumHard.getInstance()?.kidsAccessibility = attributesObject
                viewState?.showTitle(resourceManager.getString(R.string.om_kidsAccessibility))
            }
        }
        calculateAvailabilityZone(attributesObject)
    }

    fun onResetBtnClicked(){
        dataList.forEach {
            if (it.type == AddObject.TYPE_CONTENT){
                it.value = resourceManager.getString(R.string.not_accessible)
                it.valueToUpload = AppConstants.OBJECT_ATTR_NOT_PROVIDED
            }
        }
        viewState?.showUIData(dataList)
        initForm("")
    }

    private fun calculateAvailabilityZone(jsonObject: JsonObject){
        jsonObject.addProperty("type", "${type.replace("1", "")}_${CreateObjectMediumHard.getInstance()?.form}")
        objectInteractor.calculateAvailability(jsonObject)
            .subscribe(
                {
                    viewState?.showAvailabilityImages(
                        getAvailabilityDrawable(it.movement ?: ""),
                        getAvailabilityDrawable(it.limb ?: ""),
                        getAvailabilityDrawable(it.vision ?: ""),
                        getAvailabilityDrawable(it.hearing ?: ""),
                        getAvailabilityDrawable(it.intellectual ?: "")
                    )
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    private fun getAvailabilityDrawable(type: String): Int =
        when (type){
            AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE -> R.drawable.ic_45
            AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE -> R.drawable.ic_46
            AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE -> R.drawable.ic_63
            AppConstants.OVERALL_SCOPE_NOT_PROVIDED -> R.drawable.ic_105
            else -> R.drawable.ic_104
        }
}