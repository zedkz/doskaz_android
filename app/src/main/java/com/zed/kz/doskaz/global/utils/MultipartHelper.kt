package com.zed.kz.doskaz.global.utils

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File


object MultipartHelper {

    fun getFileDataFromPath(path: String?): MultipartBody.Part? {
        if(path == null) return null

        return MultipartHelper.getImageFileDataFromFile(
            "image",
            File(path)
        )
    }

    fun getFileRequestBodyFromPath(path: String?): RequestBody? {
        if(path == null) return null

        return getImageFileRequestBodyFromFile(
            File(path)
        )
    }

    fun getFileDataFromBitmap(fieldName: String, bitmap: Bitmap): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val reqFile = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_OCTET), byteArrayOutputStream.toByteArray())
        return MultipartBody.Part.createFormData(fieldName, "${System.currentTimeMillis()}.jpg", reqFile)
    }

    fun getImageFileDataFromFile(fieldName: String, file: File): MultipartBody.Part {
        val reqFile = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_OCTET), file.readBytes())
        return MultipartBody.Part.createFormData("$fieldName.png", "${System.currentTimeMillis()}.png", reqFile)
    }

    fun getImageFileRequestBodyFromFile(file: File): RequestBody {
        return RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_OCTET), file.readBytes())
    }


}