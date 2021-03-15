package com.zed.kz.doskaz.global.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


internal fun FragmentActivity.removeFragment(tag: String) {
    this.supportFragmentManager.findFragmentByTag(tag)?.let {
        this.supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .remove(it)
                .commitNow()
    }
}

internal fun FragmentActivity.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    for (i in 0..this.supportFragmentManager.backStackEntryCount) this.supportFragmentManager.popBackStack()

    this.supportFragmentManager
        .beginTransaction()
        .disallowAddToBackStack()
        .add(containerViewId, fragment, tag)
        .commit()
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                          fragment: Fragment,
                                          tag: String) {
    for (i in 0..this.backStackEntryCount) this.popBackStack()

    this.beginTransaction()
        .disallowAddToBackStack()
        .add(containerViewId, fragment, tag)
        .commit()
}


internal fun FragmentActivity.replaceFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {

    for (i in 0..this.supportFragmentManager.backStackEntryCount) this.supportFragmentManager.popBackStack()

    this.supportFragmentManager
        .beginTransaction()
        .disallowAddToBackStack()
        .replace(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

internal fun FragmentManager.replaceFragment(containerViewId: Int,
                                              fragment: Fragment,
                                              tag: String) {

    for (i in 0..this.backStackEntryCount) this.popBackStack()

    this.beginTransaction()
        .disallowAddToBackStack()
        .replace(containerViewId, fragment, tag)
        .commitAllowingStateLoss()
}

internal fun FragmentActivity.replaceFragmentWithBackStack(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String) {
    this.supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment, tag)
            .addToBackStack(tag)
            .commit()
}

internal fun FragmentActivity.addFragmentWithBackStack(containerViewId: Int,
                                                          fragment: Fragment,
                                                          tag: String) {
    this.supportFragmentManager
        .beginTransaction()
        .add(containerViewId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}

internal fun FragmentManager.addFragmentWithBackStack(containerViewId: Int,
                                                       fragment: Fragment,
                                                       tag: String) {
    this.beginTransaction()
        .add(containerViewId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}
