package com.zed.kz.doskaz.global.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.ByteArrayOutputStream
import java.io.File

fun File.compressImageGlide(
    context: Context,
    width: Int,
    height: Int,
    onImageCompressListener: (File) -> Unit
){
    Glide.with(context)
        .asBitmap()
        .load(this)
        .override(width, height)
        .addListener(object: RequestListener<Bitmap>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {

                return false
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {



                return true
            }
        })
        .submit()
}

fun File.compressImage(quality: Int){
    val bitmap = BitmapFactory.decodeFile(this.path)
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    this.writeBytes(byteArrayOutputStream.toByteArray())
}

fun File.reduceSizeImage(width: Int, height: Int){
    var bitmap = BitmapFactory.decodeFile(this.path)
    var tempWidth = bitmap.width
    if (bitmap.width > (width + 150))
        tempWidth = bitmap.width - width

    var tempHeigth = bitmap.height
    if (bitmap.height > (height + 150))
        tempHeigth = bitmap.height - height

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap = Bitmap.createScaledBitmap(bitmap, tempWidth, tempHeigth, false)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    this.writeBytes(byteArrayOutputStream.toByteArray())
}
