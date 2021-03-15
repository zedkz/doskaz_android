package com.zed.kz.doskaz.main.data.repository

import com.zed.kz.doskaz.entity.Award
import com.zed.kz.doskaz.entity.Pagination
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.service.ServerService
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody

class UserRepositoryImpl(
    private val serverService: ServerService
) : UserRepository{

    override fun getProfile(cityId: Int?): Single<User> =
        serverService.getProfile(cityId)

    override fun editProfile(user: User): Completable =
        serverService.editProfile(user)

    override fun editProfileAvatar(avatar: MultipartBody.Part): Completable =
        serverService.editProfileAvatar(avatar)

    override fun getMyTasks(sort: String, page: Int): Single<Pagination> =
        serverService.getMyTasks(sort, page)

    override fun getMyObjects(overallScore: String, sort: String, page: Int): Single<Pagination> =
        serverService.getMyObjects(overallScore, sort, page)

    override fun getMyComments(sort: String, page: Int): Single<Pagination> =
        serverService.getMyComments(sort, page)

    override fun getMyComplaints(sort: String, page: Int): Single<Pagination> =
        serverService.getMyComplaints(sort, page)

    override fun getMyAwards(): Single<List<Award>> =
        serverService.getMyAwards()

    override fun getMyEvents(): Single<Pagination> =
        serverService.getMyEvents()

}