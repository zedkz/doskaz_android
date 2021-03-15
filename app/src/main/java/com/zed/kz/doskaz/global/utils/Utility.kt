package com.zed.kz.doskaz.global.utils
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import java.lang.ref.WeakReference

object Utility {
    fun hexString(res: Resources?): String {
        val resImpl = getPrivateField(
            "android.content.res.Resources",
            "mResourcesImpl",
            res
        )
        val o = (resImpl ?: res)!!
        return "@" + Integer.toHexString(o.hashCode())
    }

    fun getPrivateField(
        className: String?,
        fieldName: String?,
        `object`: Any?
    ): Any? {
        return try {
            val c = Class.forName(className!!)
            val f = c.getDeclaredField(fieldName!!)
            f.isAccessible = true
            f[`object`]
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    fun resetActivityTitle(a: Activity) {
        try {
            val info = a.packageManager
                .getActivityInfo(a.componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                a.setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    // https://developer.android.com/about/versions/pie/restrictions-non-sdk-interfaces
    val titleCache: String
        get() {
            // https://developer.android.com/about/versions/pie/restrictions-non-sdk-interfaces
            if (isAtLeastVersion(Build.VERSION_CODES.P)) return "Can't access title cache\nstarting from API 28"
            val o = getPrivateField(
                "android.app.ApplicationPackageManager",
                "sStringCache",
                null
            )
            val cache =
                o as Map<*, WeakReference<CharSequence>>? ?: return ""
            val builder = StringBuilder("Cache:").append("\n")
            for ((_, value) in cache) {
                val title = value.get()
                if (title != null) {
                    builder.append(title).append("\n")
                }
            }
            return builder.toString()
        }

    fun getTopLevelResources(a: Activity): Resources {
        return try {
            a.packageManager.getResourcesForApplication(a.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
    }

    fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
}