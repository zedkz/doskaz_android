package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.form.AddObjectForm
import com.zed.kz.doskaz.entity.yandex.YandexGeocode
import io.reactivex.Single

interface ListRepository {

    fun getCategories(): Single<List<Category>>

    fun detectCity(): Single<City>

    fun getRegional(): Single<List<Regional>>

    fun getDisabilityCategories(): Single<List<DisabilityCategory>>

    fun getComplaintOptions(): Single<List<Option>>

    fun getAuthorities(): Single<List<Authority>>

    fun getCities(): Single<List<City>>

    fun getAttributes(): Single<AddObjectForm>

    fun getAddressFromLatLng(
        apikey: String,
        format: String,
        geocode: String
    ): Single<YandexGeocode>

}