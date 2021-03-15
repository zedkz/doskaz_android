package com.zed.kz.doskaz.global.utils

import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import com.zed.kz.doskaz.entity.City
import com.zed.kz.doskaz.entity.CreateComplaint
import com.zed.kz.doskaz.entity.DisabilityCategory
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.entity.object_info.Zones

object LocalStorage {

    const val PREF_NO_VAL = "no_val"

    private const val PREF_ACCESS_TOKEN = "access_token"
    const val PREF_FIRST_TIME_LAUNCHED = "first_time_launched"
    private const val PREF_USER = "user"
    const val PREF_LANG = "lang"
    private const val PREF_CURRENT_QUERY = "current_query"
    private const val PREF_OBJECT_ITEM = "object_item"
    private const val PREF_DISABILITY_CATEGORY = "disability_category"
    private const val PREF_OBJECT_INFO_ZONES = "object_info_zones"
    private const val PREF_CITY = "city"
    private const val PREF_COMPLAINT = "complaint"
    private const val PREF_LANG_CHOOSE = "lang_choose"

    fun setAccessToken(accessToken: String) = Prefs.putString(PREF_ACCESS_TOKEN, accessToken)

    fun getAccessToken(): String = Prefs.getString(PREF_ACCESS_TOKEN, PREF_NO_VAL)

    fun setFirstTimeLaunched(isFirstTime: Boolean) = Prefs.putBoolean(PREF_FIRST_TIME_LAUNCHED, isFirstTime)

    fun isFirstTimeLaunched(): Boolean = Prefs.getBoolean(PREF_FIRST_TIME_LAUNCHED, true)

    fun setUser(user: User?) = Prefs.putString(PREF_USER, Gson().toJson(user))

    fun getUser(): User? = Gson().fromJson(Prefs.getString(PREF_USER, null), User::class.java)

    fun setLang(lang: String) = Prefs.putString(PREF_LANG, lang)

    fun getLang(): String = Prefs.getString(PREF_LANG, PREF_NO_VAL)

    fun getLangForApi(): String = Prefs.getString(PREF_LANG, PREF_NO_VAL).replace("kk", "kz")

    fun setCurrentQuery(query: String) = Prefs.putString(PREF_CURRENT_QUERY, query)

    fun getCurrentQuery(): String = Prefs.getString(PREF_CURRENT_QUERY, "")

    fun setCurrentObjectItem(objectItem: ObjectItem?) = Prefs.putString(PREF_OBJECT_ITEM, Gson().toJson(objectItem))

    fun getCurrentObjectItem(): ObjectItem = Gson().fromJson(Prefs.getString(PREF_OBJECT_ITEM, PREF_NO_VAL), ObjectItem::class.java)

    fun setCurrentDisabilityCategory(disabilityCategory: DisabilityCategory?) = Prefs.putString(PREF_DISABILITY_CATEGORY, Gson().toJson(disabilityCategory))

    fun getCurrentDisabilityCategory(): DisabilityCategory? = Gson().fromJson(Prefs.getString(PREF_DISABILITY_CATEGORY, null), DisabilityCategory::class.java)

    fun setCurrentCity(city: City) = Prefs.putString(PREF_CITY, Gson().toJson(city))

    fun getCurrentCity(): City? = Gson().fromJson(Prefs.getString(PREF_CITY, null), City::class.java)

    fun setCreateComplaint(createComplaint: CreateComplaint?) = Prefs.putString(PREF_COMPLAINT, Gson().toJson(createComplaint))

    fun getCreateComplaint(): CreateComplaint? = Gson().fromJson(Prefs.getString(PREF_COMPLAINT, null), CreateComplaint::class.java)

    fun setLangChoose(isChose: Boolean) = Prefs.putBoolean(PREF_LANG_CHOOSE, isChose)

    fun isLangChose(): Boolean = Prefs.getBoolean(PREF_LANG_CHOOSE, false)
}