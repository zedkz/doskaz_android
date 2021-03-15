package com.zed.kz.doskaz.main.presentation.profile.main.show

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.User
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.*
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.home.HomeFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.AddSimpleObjectBottomSheetFragment
import com.zed.kz.doskaz.main.presentation.profile.edit.EditProfileBottomSheetFragment
import com.zed.kz.doskaz.main.presentation.profile.my.award.MyAwardFragment
import com.zed.kz.doskaz.main.presentation.profile.my.comment.MyCommentFragment
import com.zed.kz.doskaz.main.presentation.profile.my.complaint.MyComplaintFragment
import com.zed.kz.doskaz.main.presentation.profile.my.objects.MyObjectsFragment
import com.zed.kz.doskaz.main.presentation.profile.my.task.MyTaskFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ShowProfileFragment : BaseFragment(), ShowProfileFragmentView{

    companion object{

        val TAG = "ShowProfileFragment"

        private val BUNDLE_FRAGMENT_TYPE = "fragment_type"

        fun newInstance(type: String? = null): ShowProfileFragment =
            ShowProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_FRAGMENT_TYPE, type)
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ShowProfilePresenter

    @ProvidePresenter
    fun providePresenter(): ShowProfilePresenter {
        getKoin().getScopeOrNull(MainScope.SHOW_PROFILE_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.SHOW_PROFILE_SCOPE, named(MainScope.SHOW_PROFILE_SCOPE))
        val type = arguments?.getString(BUNDLE_FRAGMENT_TYPE)
        return scope.get { parametersOf(type) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_profile

    override fun setUp(savedInstanceState: Bundle?) {
        rootProfile.setOnClickListener {  }
        imgBackToolbar.visible(true)
        imgBackToolbar.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbar.text = getString(R.string.my_profile)
        imgLogoutToolbar?.apply {
            visible(true)
            setOnClickListener {
                val builder = AlertDialog.Builder(context)
                    .setMessage(getString(R.string.profile_are_you_sure))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        presenter.onLogoutBtnClicked()
                        dialog.dismiss()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
                    .create()
                builder.show()
                context?.let {
                    builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
                    builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.colorAccent))
                }

            }
        }
        initViewsListeners()
        presenter.onFirstInit()
    }

    private fun initViewsListeners(){
        btnEditProfile?.setOnClickListener {
            activity?.addFragmentWithBackStack(
                R.id.container,
                EditProfileBottomSheetFragment.newInstance(),
                EditProfileBottomSheetFragment.TAG
            )
        }

        btnEditProfile?.setOnLongClickListener {
            activity?.replaceFragmentWithBackStack(
                R.id.container,
                AddSimpleObjectBottomSheetFragment.newInstance(),
                AddSimpleObjectBottomSheetFragment.TAG
            )
            return@setOnLongClickListener true
        }

        navViewProfile?.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_my_task -> showMyTaskFragment()
                R.id.nav_my_object -> showMyObjectsFragment()
                R.id.nav_my_comments -> showMyCommentsFragment()
                R.id.nav_my_tickets -> showMyComplaintsFragment()
                R.id.nav_my_achievement -> showMyAwardFragment()
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.SHOW_PROFILE_SCOPE)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showUserInfo(user: User) {
        imgAvatarProfile?.setImageUrl(user.avatar, R.drawable.ic_60)
        txtNameProfile?.text = "${user.lastName ?: ""} ${user.firstName ?: ""} ${user.middleName ?: ""}"
        txtStatusProfile?.text = user.status
        txtLevelProfile?.text = "${user.level?.current ?: ""} ${getString(R.string.level).toLowerCase()}"
        prgLevelProfile?.progress = (((user.level?.currentPoints ?: 0).toDouble() / (user.level?.nextLevelThreshold ?: 0).toDouble()) * 100).toInt()
        txtCurrentLevelProfile?.text = "${user.level?.currentPoints ?: 0} / ${user.level?.nextLevelThreshold ?: 0}"
        txtCurrentTaskProfile?.text = "${user.currentTask?.progress ?: 0} %"
        prgTaskProfile?.progress = user.currentTask?.progress ?: 0
        txtTaskTextProfile?.text = user.currentTask?.title
        txtObjectProfile?.text = "${user.stats?.objects ?: 0} ${getString(R.string.`object`).toLowerCase()}"
        txtComplaintProfile?.text = "${user.stats?.complaints ?: 0} ${getString(R.string.complaint).toLowerCase()}"
        txtChecklistProfile?.text = "0 ${getString(R.string.checklist).toLowerCase()}"
    }

    override fun showMyTaskFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_profile,
            MyTaskFragment.newInstance(),
            MyTaskFragment.TAG
        )
    }

    override fun showMyObjectsFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_profile,
            MyObjectsFragment.newInstance(),
            MyObjectsFragment.TAG
        )
    }

    override fun showMyCommentsFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_profile,
            MyCommentFragment.newInstance(),
            MyCommentFragment.TAG
        )
    }

    override fun showMyComplaintsFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_profile,
            MyComplaintFragment.newInstance(),
            MyComplaintFragment.TAG
        )
    }

    override fun showMyAwardFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_profile,
            MyAwardFragment.newInstance(),
            MyAwardFragment.TAG
        )
    }

    override fun setNavigationSelectedItemId(id: Int) {
        navViewProfile?.selectedItemId = id
    }

    override fun openHomeFragment() {
        activity?.replaceFragment(
            R.id.container,
            HomeFragment.newInstance(),
            HomeFragment.TAG
        )
    }
}