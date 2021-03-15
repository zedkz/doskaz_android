package com.zed.kz.doskaz.main.data.repository

import android.content.Context
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.form.AddObjectForm
import com.zed.kz.doskaz.entity.yandex.YandexGeocode
import com.zed.kz.doskaz.global.service.ServerService
import com.zed.kz.doskaz.global.utils.GsonHelper
import io.reactivex.Single
import timber.log.Timber

class ListRepositoryImpl(
    private val context: Context,
    private val serverService: ServerService
): ListRepository{

    override fun getCategories(): Single<List<Category>> =
        serverService.getCategories()

    override fun detectCity(): Single<City> =
        serverService.detectCity()

    override fun getRegional(): Single<List<Regional>> =
        serverService.getRegional()

    override fun getDisabilityCategories(): Single<List<DisabilityCategory>> =
        Single.create<List<DisabilityCategory>> {
            val inputStream = context.resources.openRawResource(R.raw.disability_categories)
            val text = inputStream.bufferedReader().readText()
            val list = GsonHelper.getModelArray(text, DisabilityCategory::class.java)
            it.onSuccess(list)
        }

    override fun getComplaintOptions(): Single<List<Option>> =
        serverService.getComplaintOptions()

    override fun getAuthorities(): Single<List<Authority>> =
        serverService.getAuthorities()

    override fun getCities(): Single<List<City>> =
        serverService.getCities()

    override fun getAttributes(): Single<AddObjectForm> =
        serverService.getAttributes()

    override fun getAddressFromLatLng(
        apikey: String,
        format: String,
        geocode: String
    ): Single<YandexGeocode> =
        serverService.getAddressFromLatLng(apikey, format, geocode)
}