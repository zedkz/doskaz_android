package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.Award
import com.zed.kz.doskaz.entity.Pagination
import com.zed.kz.doskaz.entity.User
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody

interface UserRepository {

    fun getProfile(cityId: Int?): Single<User>

    fun editProfile(user: User): Completable

    fun editProfileAvatar(avatar: MultipartBody.Part): Completable

    fun getMyTasks(sort: String, page: Int): Single<Pagination>

    fun getMyObjects(overallScore: String, sort: String, page: Int): Single<Pagination>

    fun getMyComments(sort: String, page: Int): Single<Pagination>

    fun getMyComplaints(sort: String, page: Int): Single<Pagination>

    fun getMyAwards(): Single<List<Award>>

    fun getMyEvents(): Single<Pagination>

}