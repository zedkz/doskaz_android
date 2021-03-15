package com.zed.kz.doskaz.main.data.repository

import com.google.gson.JsonObject
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.entity.object_info.Review
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.RequestBody

interface ObjectRepository {

    fun getObjectsOnMap(
        zoom: Int,
        bbox: String,
        query: String?,
        disabilitiesCategory: String?,
        subCategoryId: Int?,
        accessibilityLevels: Map<String, String>?): Single<MapItem>

    fun filterObjects(
        cityId: Int,
        disabilitiesCategory: String?,
        subCategoryId: Int?,
        accessibilityLevels: Map<String, String>?
    ): Single<List<ObjectItem>>

    fun searchObjects(
        cityId: Int,
        query: String
    ): Single<List<ObjectItem>>

    fun getObjectById(objectId: Int): Single<ObjectItem>

    fun objectVerification(
        objectId: Int,
        status: String
    ): Completable

    fun createObjectReview(
        id: Int,
        review: Review
    ): Completable

    fun upload(body: RequestBody?): Single<Upload>

    fun createObjectValidate(
        createObject: CreateObject
    ): Completable

    fun createObject(
        createObject: CreateObject
    ): Completable

    fun createComplaint(
        createComplaint: CreateComplaint
    ): Single<ComplaintResponse>

    fun calculateAvailability(
        formJsonObject: JsonObject
    ): Single<AvailabilityZone>

    fun createObjectValidate(
        createObjectMediumHard: CreateObjectMediumHard
    ): Completable

    fun createObject(
        createObjectMediumHard: CreateObjectMediumHard
    ): Completable

    fun uploadObjectPhotos(
        objectId: Int,
        photoRequest: PhotoRequest
    ): Completable
}