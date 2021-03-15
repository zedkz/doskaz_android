package com.zed.kz.doskaz.main.presentation.main.objects.description

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.objects.complaint.ObjectComplaintFragment
import com.zed.kz.doskaz.main.presentation.main.objects.dialog.ObjectReviewDialogFragment
import com.zed.kz.doskaz.main.presentation.main.objects.dialog.ObjectVerificationDialogFragment
import com.zed.kz.doskaz.main.presentation.main.objects.info.category.ObjectInfoCategoryFragment
import com.zed.kz.doskaz.main.presentation.main.objects.main.MainObjectBottomSheetFragment
import com.zed.kz.doskaz.main.presentation.main.objects.review.create.CreateObjectReviewFragment
import kotlinx.android.synthetic.main.fragment_object_description.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class DescriptionObjectFragment : BaseFragment(), DescriptionObjectFragmentView{

    companion object{

        val TAG = "DescriptionObjectFragment"

        fun newInstance(): DescriptionObjectFragment =
            DescriptionObjectFragment()
    }

    @InjectPresenter
    lateinit var presenter: DescriptionObjectPresenter

    @ProvidePresenter
    fun providePresenter(): DescriptionObjectPresenter {
        getKoin().getScopeOrNull(MainScope.DESCRIPTION_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.DESCRIPTION_OBJECT_SCOPE, named(MainScope.DESCRIPTION_OBJECT_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_object_description

    override fun setUp(savedInstanceState: Bundle?) {
        presenter.onFirstInit()
        txtMoreInfoObjectDescription?.setOnClickListener { presenter.showMoreBtnClicked() }
        btnVerifiedObjectDescription?.setOnClickListener { presenter.onVerificationBtnClicked() }
        btnComplaintObjectDescription?.setOnClickListener { presenter.onComplaintBtnClicked() }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.DESCRIPTION_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showDescription(description: String) {
        txtDescriptionObjectDescription?.text = description
    }

    override fun showVerifiedText(text: String) {
        txtVerifiedObjectDescription?.text = text
    }

    override fun openObjectVerificationDialogFragment(objectName: String) {
        activity?.supportFragmentManager?.let {
            ObjectVerificationDialogFragment.newInstance(
                objectName,
                { presenter.objectVerification(AppConstants.VERIFICATION_STATUS_CONFIRM) },
                { presenter.objectVerification(AppConstants.VERIFICATION_STATUS_REJECT) }
            ).show(it, ObjectVerificationDialogFragment.TAG)
        }
    }

    override fun openObjectReviewDialogFragment() {
        activity?.supportFragmentManager?.let {
            ObjectReviewDialogFragment.newInstance { openCreateReviewDialogFragment() }
                .show(it, ObjectReviewDialogFragment.TAG)
        }
    }

    override fun openCreateReviewDialogFragment() {
        activity?.supportFragmentManager?.let {
            CreateObjectReviewFragment.newInstance {  }
                .show(it, CreateObjectReviewFragment.TAG)
        }
    }

    override fun openObjectComplaintFragment(objectId: Int) {
        activity?.supportFragmentManager?.let {
            val objectComplaintFragment = ObjectComplaintFragment.newInstance(objectId)
            objectComplaintFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogFragmentTheme)
            objectComplaintFragment.show(
                it,
                ObjectComplaintFragment.TAG
            )
        }
    }

    override fun openSignInFragment() {
        MainObjectBottomSheetFragment.getInstance()?.dismiss()
        activity?.supportFragmentManager
            ?.addFragmentWithBackStack(
                R.id.container,
                SignInFragment.newInstance(),
                SignInFragment.TAG
            )
    }

    override fun openObjectInfoCategoryFragment() {
        activity?.supportFragmentManager?.let {
            ObjectInfoCategoryFragment.newInstance()
                .show(it, ObjectInfoCategoryFragment.TAG)
        }
    }
}