package com.zed.kz.doskaz.global.extension

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber
import java.io.File

fun Uri.getFile(context: Context?, onFileReadyListener: (File) -> Unit){
    if (context != null) {
        val uri = Uri.parse(this.path?.replace("/raw/", ""))
        Glide.with(context)
            .asFile()
            .load(uri)
            .addListener(object: RequestListener<File> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<File>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.printStackTrace()
                    uri.path?.let {
                        onFileReadyListener.invoke(File(it))
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: File?,
                    model: Any?,
                    target: Target<File>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.d(resource?.absolutePath)
                    resource?.let {
                        onFileReadyListener.invoke(it)
                    }
                    return true
                }
            })
            .submit()
    }
}