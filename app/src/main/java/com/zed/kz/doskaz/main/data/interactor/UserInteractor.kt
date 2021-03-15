package com.zed.kz.doskaz.main.data.interactor

import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.global.system.SchedulersProvider
import com.zed.kz.doskaz.global.utils.GsonHelper
import com.zed.kz.doskaz.main.data.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody

class UserInteractor(
    private val schedulersProvider: SchedulersProvider,
    private val userRepository: UserRepository
){

    fun getProfile(cityId: Int? = null): Single<User> =
        userRepository.getProfile(cityId)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun editProfile(user: User): Completable =
        userRepository.editProfile(user)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun editProfileAvatar(avatar: MultipartBody.Part): Completable =
        userRepository.editProfileAvatar(avatar)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyTasks(sort: String, page: Int): Single<List<Task>> =
        userRepository.getMyTasks(sort, page)
            .map {
                val tasks = GsonHelper.getModelArray(it.items, Task::class.java)
                tasks
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyObjects(overallScore: String, sort: String, page: Int): Single<List<MyObject>> =
        userRepository.getMyObjects(overallScore, sort, page)
            .map {
                val objects = GsonHelper.getModelArray(it.items, MyObject::class.java)
                objects
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyComments(sort: String, page: Int): Single<List<Comment>> =
        userRepository.getMyComments(sort, page)
            .map {
                val comments = GsonHelper.getModelArray(it.items, Comment::class.java)
                comments
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyComplaints(sort: String, page: Int): Single<List<Complaint>> =
        userRepository.getMyComplaints(sort, page)
            .map {
                val complaints = GsonHelper.getModelArray(it.items, Complaint::class.java)
                complaints
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyAwards(): Single<List<Award>> =
        userRepository.getMyAwards()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

    fun getMyEvents(): Single<List<Event>> =
        userRepository.getMyEvents()
            .map {
                val events = GsonHelper.getModelArray(it.items, Event::class.java)
                events
            }
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())

}