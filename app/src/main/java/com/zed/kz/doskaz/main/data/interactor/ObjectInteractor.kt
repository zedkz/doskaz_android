package com.zed.kz.doskaz.main.data.interactor

import com.google.gson.JsonObject
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.global.utils.LocalStorage
import com.zed.kz.doskaz.global.utils.MultipartHelper
import com.zed.kz.doskaz.main.data.repository.ObjectRepository
import io.reactivex.Completable
import io.reactivex.Single

class ObjectInteractor(
    private val schedulerProvider: SchedulersProvider,
    private val objectRepository: ObjectRepository
){

    fun getObjectsOnMap(
        zoom: Int,
        bbox: String,
        query: String? = null,
        subCategoryId: Int? = null,
        disabilitiesCategory: String? = LocalStorage.getCurrentDisabilityCategory()?.categoryForAPI,
        accessibilityLevels: Map<String, String>? = mapOf()): Single<MapItem> =
        objectRepository.getObjectsOnMap(zoom, bbox, query, disabilitiesCategory, subCategoryId, accessibilityLevels)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun filterObjects(
        cityId: Int,
        disabilitiesCategory: String? = null,
        subCategoryId: Int? = null,
        accessibilityLevels: Map<String, String>? = null
    ): Single<List<ObjectItem>> =
        objectRepository.filterObjects(cityId, disabilitiesCategory, subCategoryId, accessibilityLevels)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun searchObjects(cityId: Int, query: String): Single<List<ObjectItem>> =
        objectRepository.searchObjects(cityId, query)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun getObjectById(objectId: Int): Single<ObjectItem> =
        objectRepository.getObjectById(objectId)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun objectVerification(
        objectId: Int,
        status: String
    ): Completable =
        objectRepository.objectVerification(objectId, status)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createObjectReview(
        id: Int,
        review: Review
    ): Completable =
        objectRepository.createObjectReview(id, review)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun upload(path: String?): Single<Upload> =
        objectRepository.upload(MultipartHelper.getFileRequestBodyFromPath(path))
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createObjectValidate(
        createObject: CreateObject
    ): Completable =
        objectRepository.createObjectValidate(createObject)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createObject(
        createObject: CreateObject
    ): Completable =
        objectRepository.createObject(createObject)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createComplaint(
        createComplaint: CreateComplaint
    ): Single<ComplaintResponse> =
        objectRepository.createComplaint(createComplaint)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun calculateAvailability(
        formJsonObject: JsonObject
    ): Single<AvailabilityZone> =
        objectRepository.calculateAvailability(formJsonObject)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createObjectValidate(
        createObjectMediumHard: CreateObjectMediumHard
    ): Completable =
        objectRepository.createObjectValidate(createObjectMediumHard)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun createObject(
        createObjectMediumHard: CreateObjectMediumHard
    ): Completable =
        objectRepository.createObject(createObjectMediumHard)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    fun uploadObjectPhotos(objectId: Int, photoRequest: PhotoRequest): Completable =
        objectRepository.uploadObjectPhotos(objectId, photoRequest)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
}