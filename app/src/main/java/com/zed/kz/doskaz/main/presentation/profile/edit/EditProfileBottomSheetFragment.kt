package com.zed.kz.doskaz.main.presentation.profile.edit

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.base.PhotoBaseBottomSheetFragment
import com.zed.kz.doskaz.global.base.PhotoBaseFragment
import com.zed.kz.doskaz.global.base.pickPhotoFromGalleryWithPermissionCheck
import com.zed.kz.doskaz.global.extension.setImageUrl
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class EditProfileBottomSheetFragment : PhotoBaseFragment(), EditProfileFragmentView{

    companion object{

        val TAG = "EditProfileFragment"

        fun newInstance(): EditProfileBottomSheetFragment =
            EditProfileBottomSheetFragment()
    }

    @InjectPresenter
    lateinit var presenter: EditProfilePresenter

    @ProvidePresenter
    fun providePresenter(): EditProfilePresenter {
        getKoin().getScopeOrNull(MainScope.EDIT_PROFILE_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.EDIT_PROFILE_SCOPE, named(MainScope.EDIT_PROFILE_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_profile_edit

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbar.visible(true)
        imgBackToolbar.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbar.text = getString(R.string.my_profile)
        presenter.onFirstInit()
        btnSaveProfileEdit?.setOnClickListener {
            presenter.editProfile(
                lastName = tieLastNameProfileEdit.text.toString(),
                firstName = tieFirstNameProfileEdit.text.toString(),
                middleName = tieMiddleNameProfileEdit.text.toString(),
                email = tieEmailProfileEdit.text.toString(),
                phone = tiePhoneProfileEdit.rawText.toString(),
                status = tieStatusProfileEdit.text.toString(),
                sms = tieSmsProfileEdit.text.toString()
            )
        }

        imgAvatarProfileEdit?.setOnClickListener {
            pickPhotoFromGalleryWithPermissionCheck(true)
        }

        txtGetSmsProfileEdit?.setOnClickListener {
            activity?.let { it1 -> presenter.onGetSmsBtnClicked(it1) }
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.EDIT_PROFILE_SCOPE)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showUserInfo(user: User) {
        imgAvatarProfileEdit?.setImageUrl(user.avatar, R.drawable.ic_60)
        txtNameProfileEdit?.text = "${user.lastName ?: ""} ${user.firstName ?: ""} ${user.middleName ?: ""}"
        txtStatusProfileEdit?.text = user.status
        txtLevelProfileEdit?.text = "${user.level?.current ?: ""} ${getString(R.string.level).toLowerCase()}"
        prgLevelProfileEdit?.progress = (((user.level?.currentPoints ?: 0).toDouble() / (user.level?.nextLevelThreshold ?: 0).toDouble()) * 100).toInt()
        txtCurrentLevelProfileEdit?.text = "${user.level?.currentPoints ?: 0} / ${user.level?.nextLevelThreshold ?: 0}"
        txtCurrentTaskProfileEdit?.text = "${user.currentTask?.progress ?: 0} %"
        prgTaskProfileEdit?.progress = user.currentTask?.progress ?: 0
        txtTaskTextProfileEdit?.text = user.currentTask?.title
        txtObjectProfileEdit?.text = "${user.stats?.objects ?: 0} ${getString(R.string.`object`).toLowerCase()}"
        txtComplaintProfileEdit?.text = "${user.stats?.complaints ?: 0} ${getString(R.string.complaint).toLowerCase()}"
        txtChecklistProfileEdit?.text = "0 ${getString(R.string.checklist).toLowerCase()}"
        tieLastNameProfileEdit?.setText(user.lastName)
        tieFirstNameProfileEdit?.setText(user.firstName)
        tieMiddleNameProfileEdit?.setText(user.middleName)
        tieEmailProfileEdit?.setText(user.email)
        //tiePhoneProfileEdit?.setText(user.phone)
        tieStatusProfileEdit?.setText(user.status)
    }

    override fun setImage(uri: Uri) {

    }

    override fun setVideo(uri: Uri) {

    }

    override fun setImagePath(path: String) {
        imgAvatarProfileEdit?.setImageURI(Uri.parse(path))
        presenter.setImagePath(path)
    }

    override fun setImagePaths(paths: List<String>) {

    }

    override fun setVideoPath(path: String) {

    }
}