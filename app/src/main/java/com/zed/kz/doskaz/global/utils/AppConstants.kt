package com.zed.kz.doskaz.global.utils

object AppConstants {
    const val TEMP_IMAGE_URL = "https://www.talkwalker.com/images/2020/blog-headers/image-analysis.png"
    const val BLOG_AVA = "/static/ava.png"

    const val MIME_TYPE_TEXT = "text/plain"
    const val MIME_TYPE_IMAGE = "image/jpeg"
    const val MIME_TYPE_VIDEO = "video/mp4"
    const val MIME_TYPE_OCTET = "application/octet"

    const val SORT_CREATED_AT_ASC = "createdAt asc"
    const val SORT_CREATED_AT_DESC = "createdAt desc"
    const val SORT_DATE_ASC = "date desc"
    const val SORT_DATE_DESC = "date desc"
    const val SORT_POPULARITY_DESC = "popularity desc"
    const val SORT_CREATED_AT = "createdAt"
    const val SORT_ASC = "asc"
    const val SORT_DESC = "desc"

    const val OVERALL_SCOPE_NOT_ACCESSIBLE = "not_accessible"
    const val OVERALL_SCOPE_PARTIAL_ACCESSIBLE = "partial_accessible"
    const val OVERALL_SCOPE_FULL_ACCESSIBLE = "full_accessible"
    const val OVERALL_SCOPE_NOT_PROVIDED = "not_provided"
    const val OVERALL_SCOPE_UNKNOWN = "unknown"

    const val OBJECT_ATTR_NOT_PROVIDED = "not_provided"
    const val OBJECT_ATTR_YES = "yes"
    const val OBJECT_ATTR_NO = "no"
    const val OBJECT_ATTR_UNKNOWN = "unknown"

    const val VERIFIED_NOT = "not_verified"
    const val VERIFIED_PARTIALLY = "partially_verified"
    const val VERIFIED_FULL = "full_verified"

    const val VERIFICATION_STATUS_CONFIRM = "confirm"
    const val VERIFICATION_STATUS_REJECT = "reject"

    const val HISTORY_TYPE_VERIFICATION_CONFIRMED = "verification_confirmed"
    const val HISTORY_TYPE_VERIFICATION_REJECTED= "verification_rejected"
    const val HISTORY_TYPE_REVIEW_CREATED = "review_created"
    const val HISTORY_TYPE_SUPPLEMENTED = "supplemented"

    const val PROFILE_TYPE_EVENT = "event"
    const val PROFILE_TYPE_TASK = "task"
    const val PROFILE_TYPE_COMMENT = "comment"
    const val PROFILE_TYPE_OBJECT = "object"

    const val FORM_TYPE_SMALL = "small"
    const val FORM_TYPE_MIDDLE = "middle"
    const val FORM_TYPE_FULL = "full"

    const val ATTR_TYPE_PARKING = "parking"
    const val ATTR_TYPE_ENTRANCE = "entrance1"
    const val ATTR_TYPE_MOVEMENT = "movement"
    const val ATTR_TYPE_SERVICE = "service"
    const val ATTR_TYPE_TOILET = "toilet"
    const val ATTR_TYPE_NAVIGATION = "navigation"
    const val ATTR_TYPE_SERVICE_ACCESSIBILITY = "serviceAccessibility"
    const val ATTR_TYPE_KIDS_ACCESSIBILITY = "kidsAccessibility"
    const val ATTR_TYPE_COMMON_INFO = "common_info"

    const val RC_CATEGORY = 1
    const val RC_SUB_CATEGORY = 2
    const val RC_ADD_OBJECT = 3
    const val RC_CITY = 4
    const val RC_COMPLAINT_CITY_1 = 19
    const val RC_COMPLAINT_CITY_2 = 20
    const val RC_COMPLAINT_AUTHORITY = 21
    const val RC_COMPLAINT_TYPE = 22


    const val COMPLAINT_TYPE_1 = "complaint1"
    const val COMPLAINT_TYPE_2 = "complaint2"

    const val PAGE_SIZE = 20
    const val PAGE_SIZE_BLOG = 10

    val SMS_RESEND_TIMEOUT = 60L

    const val MAP_LAT = 43.2364108
    const val MAP_LNG = 76.8942799
    const val MAP_ZOOM = 14

    const val LANG_KZ = "kk"
    const val LANG_RU = "ru"

    const val FOLDER_IMAGE = "image"
    const val FOLDER_VIDEO = "video"

    const val TOKEN = "token"

    const val INTENT_FILTER_VK = "vk"

    const val FILTER_MENU = "filter_menu"
    const val FILTER_DOWNLOAD_FILE = "download_file"
    const val FILTER_OBJECT_CREATED = "object_created"

    const val BUNDLE_DOC_ID = "doc_id"
    const val BUNDLE_JUST_DOWNLOAD = "just_download"

    const val COMMENT_TYPE_POST = "post"
}