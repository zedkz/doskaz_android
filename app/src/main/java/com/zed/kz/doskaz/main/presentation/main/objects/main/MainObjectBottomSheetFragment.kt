package com.zed.kz.doskaz.main.presentation.main.objects.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.IconicsSize
import com.mikepenz.iconics.utils.backgroundColorInt
import com.mikepenz.iconics.utils.padding
import com.mikepenz.iconics.utils.roundedCornersPx
import com.mikepenz.iconics.utils.sizePx
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.ObjectItem
import com.zed.kz.doskaz.global.base.*
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.objects.description.DescriptionObjectFragment
import com.zed.kz.doskaz.main.presentation.main.objects.history.HistoryObjectFragment
import com.zed.kz.doskaz.main.presentation.main.objects.photo.PhotoObjectFragment
import com.zed.kz.doskaz.main.presentation.main.objects.review.create.CreateObjectReviewFragment
import com.zed.kz.doskaz.main.presentation.main.objects.review.main.ReviewObjectFragment
import com.zed.kz.doskaz.main.presentation.main.objects.video.VideoObjectFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.PhotoAdapter
import kotlinx.android.synthetic.main.fragment_object_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import timber.log.Timber

class MainObjectBottomSheetFragment : PhotoBaseBottomSheetFragment(), MainObjectFragmentView{

    companion object{

        private var mainObjectBottomSheetFragment: MainObjectBottomSheetFragment? = null

        val TAG = "MainObjectFragment"

        private val BUNDLE_OBJECT_ID = "object_id"

        const val STATE_REVIEW = "review"

        fun newInstance(objectId: Int): MainObjectBottomSheetFragment {
            mainObjectBottomSheetFragment = MainObjectBottomSheetFragment().apply {
                    arguments = Bundle().apply {
                        putInt(BUNDLE_OBJECT_ID, objectId)
                    }
                }
            return mainObjectBottomSheetFragment!!
        }

        fun getInstance(): MainObjectBottomSheetFragment? = mainObjectBottomSheetFragment

    }

    private var choosePhotoBottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>? = null

    override val layoutRes: Int
        get() = R.layout.fragment_object_main

    @InjectPresenter
    lateinit var presenter: MainObjectPresenter

    @ProvidePresenter
    fun providePresenter(): MainObjectPresenter {
        getKoin().getScopeOrNull(MainScope.MAIN_OBJECT_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.MAIN_OBJECT_SCOPE, named(MainScope.MAIN_OBJECT_SCOPE))
        val objectId = arguments?.getInt(BUNDLE_OBJECT_ID)
        return scope.get { parametersOf(objectId) }
    }

    private val photoAdapter = PhotoAdapter(
        { pickPhotoFromGalleryWithPermissionCheck(false) },
        { }
    )

    override fun setUp(savedInstanceState: Bundle?) {
        view?.post {
            dialog?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            view?.invalidate()
        }

        presenter.onFirstInit()
        navViewObjectMain?.setOnNavigationItemSelectedListener {

            fabPhotoObjectPhoto.visible(false)
            fabReviewObjectPhoto.visible(false)
            when(it.itemId){
                R.id.nav_description_om -> {
                    openDescriptionObjectFragment()
                }
                R.id.nav_photo_om -> {
                    openPhotoObjectFragment()
                    fabPhotoObjectPhoto.visible(true)
                }
                R.id.nav_feedback_om -> {
                    fabReviewObjectPhoto.visible(true)
                    openReviewObjectFragment()
                }
                R.id.nav_video_om -> {
                    openVideoObjectFragment()
                }
                R.id.nav_history_om -> {
                    openHistoryObjectFragment()
                }
            }

            return@setOnNavigationItemSelectedListener true
        }

        fabPhotoObjectPhoto?.setOnClickListener { expandChoosePhotoBottomSheet() }
        fabReviewObjectPhoto?.setOnClickListener { presenter.onCreateReviewBtnClicked() }
        //imgCameraObjectMain?.setOnClickListener { pickPhotoFromGalleryWithPermissionCheck() }
        //txtGalleryObjectMain?.setOnClickListener { pickPhotoFromGalleryWithPermissionCheck() }
        btnImageUploadObjectMain?.setOnClickListener { presenter.onUploadBtnClicked() }
        recyclerChoosePhotoObjectMain?.adapter = photoAdapter
        txtGalleryObjectMain?.visible(false)
        initChoosePhotoBottomSheet()
    }

    private fun initChoosePhotoBottomSheet(){
        choosePhotoBottomSheetBehavior = BottomSheetBehavior.from(constraintChoosePhotoObjectMain)
        choosePhotoBottomSheetBehavior?.isHideable = true
        choosePhotoBottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
        choosePhotoBottomSheetBehavior?.peekHeight = resources.getDimension(R.dimen.bottom_height).toInt()
        choosePhotoBottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){
                    //BottomSheetBehavior.STATE_EXPANDED -> viewChoosePhotoObjectMain.visible(true)
                    BottomSheetBehavior.STATE_EXPANDED -> viewChoosePhotoObjectMain.visible(true)
                    BottomSheetBehavior.STATE_HIDDEN -> viewChoosePhotoObjectMain.visible(false)
                }
            }
        })
    }

    private fun expandChoosePhotoBottomSheet(){
        viewChoosePhotoObjectMain.visible(true)
        choosePhotoBottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MAIN_OBJECT_SCOPE)
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    override fun showObjectInfo(objectItem: ObjectItem) {
        objectItem.apply {
            txtTitleObjectMain?.text = title
            txtAddressObjectMain?.text = address
            txtCategoryObjectMain?.text = "$category > $subCategory"
            imgOverallScopeObjectMain?.setImageResource(getAccessibilityIcon(overallScore))
            txtOverallScopeObjectMain?.text = when(overallScore){
                AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE -> getString(R.string.not_accessible)
                AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE -> getString(R.string.partial_accessible)
                AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE -> getString(R.string.full_accessible)
                else -> getString(R.string.not_accessible)
            }
            context?.let { context ->
                val icon = IconicsDrawable(context, (icon ?: "").replace("fa-", "faw-"))
                icon.setTint(Color.WHITE)
                icon.backgroundColorInt = Color.parseColor(objectItem.color)
                icon.sizePx = 100
                icon.padding{ IconicsSize.px(20) }
                icon.roundedCornersPx = 10f
                imgIconObjectMain?.setImageDrawable(icon)

                viewBackgroundObjectMain?.setBackgroundColor(
                    when(overallScore){
                        AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE -> ContextCompat.getColor(context, R.color.colorNotAccessible)
                        AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE -> ContextCompat.getColor(context, R.color.colorPartiallyAccessible)
                        AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE -> ContextCompat.getColor(context, R.color.colorFullAccessible)
                        else -> ContextCompat.getColor(context, R.color.colorNotAccessible)
                    }
                )
            }
            scoreByZones?.apply {
                imgParkingObjectMain?.setImageResource(getAccessibilityIcon(parking))
                imgEntranceObjectMain?.setImageResource(getAccessibilityIcon(entrance))
                imgMovementObjectMain?.setImageResource(getAccessibilityIcon(movement))
                imgServiceObjectMain?.setImageResource(getAccessibilityIcon(service))
                imgToiletObjectMain?.setImageResource(getAccessibilityIcon(toilet))
                imgNavigationObjectMain?.setImageResource(getAccessibilityIcon(navigation))
                imgServiceAvailabilityObjectMain?.setImageResource(getAccessibilityIcon(serviceAccessibility))
                imgKidsAccessibilityObjectMain?.setImageResource(getAccessibilityIcon(kidsAccessibility))
            }
        }
    }

    private fun getAccessibilityIcon(status: String?): Int {
        return when(status){
            AppConstants.OVERALL_SCOPE_NOT_ACCESSIBLE -> R.drawable.ic_45
            AppConstants.OVERALL_SCOPE_PARTIAL_ACCESSIBLE -> R.drawable.ic_46
            AppConstants.OVERALL_SCOPE_FULL_ACCESSIBLE -> R.drawable.ic_63
            else -> R.drawable.ic_104
        }
    }

    override fun openDescriptionObjectFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_object_main,
            DescriptionObjectFragment.newInstance(),
            DescriptionObjectFragment.TAG
        )
    }

    override fun openPhotoObjectFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_object_main,
            PhotoObjectFragment.newInstance(),
            PhotoObjectFragment.TAG
        )
    }

    override fun openReviewObjectFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_object_main,
            ReviewObjectFragment.newInstance(),
            ReviewObjectFragment.TAG
        )
    }

    override fun openVideoObjectFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_object_main,
            VideoObjectFragment.newInstance(),
            VideoObjectFragment.TAG
        )
    }

    override fun openHistoryObjectFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_object_main,
            HistoryObjectFragment.newInstance(),
            HistoryObjectFragment.TAG
        )
    }

    override fun openSignInFragment() {
        dismiss()
        activity?.supportFragmentManager
            ?.addFragmentWithBackStack(
                R.id.container,
                SignInFragment.newInstance(),
                SignInFragment.TAG
            )
    }

    override fun openCreateObjectReviewFragment() {
        activity?.supportFragmentManager?.let {
            CreateObjectReviewFragment.newInstance { presenter.refreshObject(STATE_REVIEW) }
                .show(it, CreateObjectReviewFragment.TAG)
        }
    }

    override fun setImage(uri: Uri) {
    }

    override fun setImagePath(path: String) {

    }

    override fun setImagePaths(paths: List<String>) {
        presenter.setImages(paths)
    }

    override fun setVideo(uri: Uri) {

    }

    override fun setVideoPath(path: String) {

    }

    override fun showPhotoData(dataList: MutableList<String>) {
        photoAdapter.submitData(dataList)
    }

    override fun hideChoosePhotoBottomSheetBehavior() {
        choosePhotoBottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun showLocalProgressBar(show: Boolean) {
        progressBarObjectMain.visible(show)
        btnImageUploadObjectMain.isEnabled = !show
    }
}