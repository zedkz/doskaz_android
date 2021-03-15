package com.zed.kz.doskaz.global.extension

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.di.ServiceProperties

//val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer!!

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible(visible: Boolean?) {
    this.visibility = if (visible ?: false) View.VISIBLE else View.GONE
}

fun View.invisible(invisible: Boolean) {
        this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

fun ImageView.setImageUrl(url: String?, errorDrawable: Int = R.drawable.no_image) {
        Glide.with(this.context.applicationContext)
                .load("${ServiceProperties.SERVER_URL}$url")
                //.load(url)
                .error(errorDrawable)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
}

fun ImageView.setCircleImageUrl(url: String?, errorDrawable: Int = R.drawable.no_image) {
        Glide.with(this.context)
                .load("${ServiceProperties.SERVER_URL}$url")
                .error(errorDrawable)
                .optionalCircleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
}

fun ImageView.setCircleImageUrl(uri: Uri?, errorDrawable: Int = R.drawable.no_image) {
        Glide.with(this.context.applicationContext)
                .load(uri)
                .error(errorDrawable)
                .optionalCircleCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

fun BaseFragment.close() = fragmentManager?.popBackStack()

fun View.navigateTo(id: Int, bundle: Bundle? = null) = Navigation.findNavController(this).navigate(id, bundle)
fun View.navigateUp() = Navigation.findNavController(this).navigateUp()

@SuppressLint("SetTextI18n")
fun TextView.setAge(ageFrom: String?, ageTo: String?) =
        if (!ageFrom.isNullOrEmpty() && ageTo.isNullOrEmpty())
                this.text = ageFrom
        else if (!ageTo.isNullOrEmpty() && ageFrom.isNullOrEmpty())
                this.text = ageTo
        else
                this.text = "$ageFrom - $ageTo"
