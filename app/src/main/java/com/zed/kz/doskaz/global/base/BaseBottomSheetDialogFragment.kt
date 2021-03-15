package com.zed.kz.doskaz.global.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.zed.kz.doskaz.R
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.zed.kz.doskaz.main.presentation.activity.MainActivity

abstract class BaseBottomSheetDialogFragment : MvpAppCompatBottomSheetDialogFragment(), BaseMvpView {

    companion object {
        private const val PROGRESS_TAG = "fragment_progress"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(savedInstanceState)
    }

    protected fun showProgressDialog(progress: Boolean) {
        if (!isAdded) return

        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager,
                PROGRESS_TAG
            )
            childFragmentManager.executePendingTransactions()
        }
    }

    abstract fun setUp(savedInstanceState: Bundle?)

    internal fun showMessage(@StringRes message: Int) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    override fun showMessage(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    override fun showLongMessage(message: String) =
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    internal fun showMessageWithAction(@StringRes message: Int, @StringRes actionText: Int,  view: View, action: () -> Any) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { action.invoke() }
        context?.let {
            snackBar.setActionTextColor(
                ContextCompat.getColor(it,
                    R.color.colorAccent))
        }
        snackBar.show()
    }

    override fun showProgressBar(show: Boolean) { if (activity is MainActivity) (activity as MainActivity).showProgressBar(show) }

}