package com.zed.kz.doskaz.main.data.interactor

import android.content.Context
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.form.AddObjectForm
import com.zed.kz.doskaz.entity.yandex.YandexGeocode
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.main.data.repository.ListRepository
import io.reactivex.Single

class ListInteractor(
    private val context: Context,
    private val schedulersProvider: SchedulersProvider,
    private val listRepository: ListRepository
){

    fun getCategories(): Single<List<Category>> =
        listRepository.getCategories()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun detectCity(): Single<City> =
        listRepository.detectCity()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getRegional(): Single<List<Regional>> =
        listRepository.getRegional()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getDisabilityCategories(): Single<List<DisabilityCategory>> =
        listRepository.getDisabilityCategories()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getComplaintOptions(): Single<List<Option>> =
        listRepository.getComplaintOptions()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getAuthorities(): Single<List<Authority>> =
        listRepository.getAuthorities()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getCities(): Single<List<City>> =
        listRepository.getCities()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getAttributes(): Single<AddObjectForm> =
        listRepository.getAttributes()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getAddressFromLatLng(
        geocode: String
    ): Single<YandexGeocode> =
        listRepository.getAddressFromLatLng(
            apikey = context.getString(R.string.yandex_api_key),
            format = "json",
            geocode = geocode
        ).subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}