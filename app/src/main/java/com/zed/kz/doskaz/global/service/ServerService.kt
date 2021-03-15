package com.zed.kz.doskaz.global.service

import com.google.gson.JsonObject
import com.zed.kz.doskaz.entity.*
import com.zed.kz.doskaz.entity.form.AddObjectForm
import com.zed.kz.doskaz.entity.medium_hard.CreateObjectMediumHard
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.entity.yandex.YandexGeocode
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface ServerService {

    @POST(Endpoints.AUTH_FIREBASE)
    fun authFirebase(
        @Body authCredential: AuthCredential
    ): Single<Response<User>>

    @GET(Endpoints.PROFILE)
    fun getProfile(
        @Query("cityId") cityId: Int?
    ): Single<User>

    @PUT(Endpoints.PROFILE)
    fun editProfile(
        @Body user: User
    ): Completable

    @Multipart
    @Headers("Content-Type: application/octet-stream")
    @POST(Endpoints.PROFILE_AVATAR)
    fun editProfileAvatar(
        @Part avatar: MultipartBody.Part
    ): Completable

    @GET(Endpoints.PROFILE_TASKS)
    fun getMyTasks(
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<Pagination>

    @GET(Endpoints.PROFILE_OBJECTS)
    fun getMyObjects(
        @Query("overallScore") overallScore: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<Pagination>

    @GET(Endpoints.PROFILE_COMMENT)
    fun getMyComments(
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<Pagination>

    @GET(Endpoints.PROFILE_COMPLAINTS)
    fun getMyComplaints(
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<Pagination>

    @GET(Endpoints.PROFILE_AWARDS)
    fun getMyAwards(): Single<List<Award>>

    @GET(Endpoints.PROFILE_EVENTS)
    fun getMyEvents(): Single<Pagination>

    @GET(Endpoints.LIST_CATEGORY)
    fun getCategories(): Single<List<Category>>

    @GET(Endpoints.CITY_DETECT)
    fun detectCity(): Single<City>

    @GET(Endpoints.OBJECT_MAP)
    fun getObjectsOnMap(
        @Query("zoom") zoom: Int,
        @Query("bbox") bbox: String,
        @Query("query") query: String?,
        @Query("disabilitiesCategory") disabilitiesCategory: String?,
        @Query("categories") subCategoryId: Int?,
        @QueryMap accessibilityLevels: Map<String, String>?
    ): Single<MapItem>

    @GET(Endpoints.OBJECT_FILTER)
    fun filterObjects(
        @Query("cityId") cityId: Int,
        @Query("disabilitiesCategory") disabilitiesCategory: String?,
        @Query("subCategoryId") subCategoryId: Int?,
        @QueryMap accessibilityLevels: Map<String, String>?
    ): Single<List<ObjectItem>>

    @GET(Endpoints.OBJECT_SEARCH)
    fun searchObjects(
        @Query("cityId") cityId: Int,
        @Query("query") query: String
    ): Single<List<ObjectItem>>

    @GET(Endpoints.OBJECT_BY_ID)
    fun getObjectById(
        @Path("id") objectId: Int
    ): Single<ObjectItem>

    @POST(Endpoints.OBJECT_VERIFICATION)
    fun objectVerification(
        @Path("id") objectId: Int,
        @Path("status") status: String
    ): Completable

    @POST(Endpoints.OBJECT_REVIEW_CREATE)
    fun createObjectReview(
        @Path("id") id: Int,
        @Body review: Review
    ): Completable

    @POST(Endpoints.FEEDBACK)
    fun sendFeedback(
        @Body feedback: Feedback
    ): Completable

    @GET(Endpoints.REGIONAL)
    fun getRegional(): Single<List<Regional>>

    @GET(Endpoints.BLOG_LIST)
    fun getBlogList(
        @Query("page") page: Int,
        @Query("categoryId") categoryId: Int?,
        @Query("period") period: String?,
        @Query("search") search: String?
    ): Single<Pagination>

    @GET(Endpoints.BLOG)
    fun getBlog(
        @Path("id") id: Int
    ): Single<BlogWrapper>

    @GET(Endpoints.BLOG_COMMENTS)
    fun getBlogComments(
        @Path("id") id: Int,
        @Query("sortBy") sortBy: String,
        @Query("sortOrder") sortOrder: String
    ): Single<BlogCommentWrapper>

    @POST(Endpoints.BLOG_COMMENTS)
    fun createBlogComment(
        @Path("id") id: Int,
        @Body blogComment: BlogComment
    ): Completable

    @GET(Endpoints.BLOG_CATEGORIES)
    fun getBlogCategories(): Single<List<BlogCategory>>

    @Headers("Content-Type: application/octet-stream")
    @POST(Endpoints.UPLOAD)
    fun upload(
        @Body body: RequestBody?
    ): Single<Upload>

    @POST(Endpoints.CREATE_OBJECT_VALIDATE)
    fun createObjectValidate(
        @Body createObject: CreateObject
    ): Completable

    @POST(Endpoints.CREATE_OBJECT_VALIDATE)
    fun createObjectValidate(
        @Body createObjectMediumHard: CreateObjectMediumHard
    ): Completable

    @POST(Endpoints.CREATE_OBJECT)
    fun createObject(
        @Body createObject: CreateObject
    ): Completable

    @POST(Endpoints.CREATE_OBJECT)
    fun createObject(
        @Body createObjectMediumHard: CreateObjectMediumHard
    ): Completable

    @GET(Endpoints.COMPLAINT_OPTIONS)
    fun getComplaintOptions(): Single<List<Option>>

    @GET(Endpoints.AUTHORITIES)
    fun getAuthorities(): Single<List<Authority>>

    @GET(Endpoints.CITIES)
    fun getCities(): Single<List<City>>

    @POST(Endpoints.CREATE_COMPLAINT)
    fun createComplaint(
        @Body createComplaint: CreateComplaint
    ): Single<ComplaintResponse>

    @GET(Endpoints.FORM_ATTRIBUTES)
    fun getAttributes(): Single<AddObjectForm>

    @POST(Endpoints.CALC_AVAILABILITY)
    fun calculateAvailability(
        @Body formJsonObject: JsonObject
    ): Single<AvailabilityZone>

    @POST(Endpoints.AUTH_GOOGLE)
    fun authGoogle(
        @Body user: User
    ): Single<Response<User>>

    @POST(Endpoints.AUTH_FACEBOOK)
    fun authFacebook(
        @Body user: User
    ): Single<Response<User>>

    @POST(Endpoints.AUTH_VK)
    fun authVk(
        @Body user: User
    ): Single<Response<User>>

    @POST(Endpoints.AUTH_MAILRU)
    fun authMailru(
        @Body user: User
    ): Single<Response<User>>

    @GET(Endpoints.YANDEX_GEOCODE)
    fun getAddressFromLatLng(
        @Query("apikey") apikey: String,
        @Query("format") format: String,
        @Query("geocode") geocode: String
    ): Single<YandexGeocode>

    @POST(Endpoints.UPLOAD_OBJECT_PHOTOS)
    fun uploadObjectPhotos(
        @Path("id") objectId: Int,
        @Body photoRequest: PhotoRequest
    ): Completable

}
