package com.thousand.santoapp.main.utils

import android.graphics.Bitmap
import com.zed.kz.doskaz.global.utils.AppConstants
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

object BitmapHelper {

    fun getFileDataFromBitmap(fieldName: String, bitmap: Bitmap?): MultipartBody.Part? {
        if (bitmap == null) return null
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)

        val reqFile = RequestBody.create(MediaType.parse(AppConstants.MIME_TYPE_IMAGE), byteArrayOutputStream.toByteArray())
        return MultipartBody.Part.createFormData(fieldName, "${System.currentTimeMillis()}.jpg", reqFile)
    }

}