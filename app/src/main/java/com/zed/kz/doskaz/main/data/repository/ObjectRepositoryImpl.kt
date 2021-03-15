package com.zed.kz.doskaz.main.data.repository

import com.google.gson.JsonObject
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody

class ObjectRepositoryImpl(
    private val serverService: ServerService
): ObjectRepository{

    override fun getObjectsOnMap(
        zoom: Int,
        bbox: String,
        query: String?,
        disabilitiesCategory: String?,
        subCategoryId: Int?,
        accessibilityLevels: Map<String, String>?
    ): Single<MapItem> =
        serverService.getObjectsOnMap(zoom, bbox,  query, disabilitiesCategory, subCategoryId, accessibilityLevels)

    override fun filterObjects(
        cityId: Int,
        disabilitiesCategory: String?,
        subCategoryId: Int?,
        accessibilityLevels: Map<String, String>?
    ): Single<List<ObjectItem>> =
        serverService.filterObjects(cityId,  disabilitiesCategory, subCategoryId, accessibilityLevels)

    override fun searchObjects(cityId: Int, query: String): Single<List<ObjectItem>> =
        serverService.searchObjects(cityId, query)

    override fun getObjectById(objectId: Int): Single<ObjectItem> =
        serverService.getObjectById(objectId)

    override fun objectVerification(
        objectId: Int,
        status: String
    ): Completable =
        serverService.objectVerification(objectId, status)

    override fun createObjectReview(id: Int, review: Review): Completable =
        serverService.createObjectReview(id, review)

    override fun upload(body: RequestBody?): Single<Upload> =
        serverService.upload(body)

    override fun createObjectValidate(createObject: CreateObject): Completable =
        serverService.createObjectValidate(createObject)

    override fun createObject(createObject: CreateObject): Completable =
        serverService.createObject(createObject)

    override fun createComplaint(createComplaint: CreateComplaint): Single<ComplaintResponse> =
        serverService.createComplaint(createComplaint)

    override fun calculateAvailability(formJsonObject: JsonObject): Single<AvailabilityZone> =
        serverService.calculateAvailability(formJsonObject)

    override fun createObject(createObjectMediumHard: CreateObjectMediumHard): Completable =
        serverService.createObject(createObjectMediumHard)

    override fun createObjectValidate(createObjectMediumHard: CreateObjectMediumHard): Completable =
        serverService.createObjectValidate(createObjectMediumHard)

    override fun uploadObjectPhotos(objectId: Int, photoRequest: PhotoRequest): Completable =
        serverService.uploadObjectPhotos(objectId, photoRequest)

}