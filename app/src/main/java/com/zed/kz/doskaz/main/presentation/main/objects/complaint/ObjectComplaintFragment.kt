package com.zed.kz.doskaz.main.presentation.main.objects.complaint

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.CreateComplaint
import com.zed.kz.doskaz.entity.ListItem
import com.zed.kz.doskaz.entity.Option
import com.zed.kz.doskaz.global.base.*
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.dialog.list.ListDialogFragment
import com.zed.kz.doskaz.main.presentation.objects.add.simple.PhotoAdapter
import com.zed.kz.doskaz.main.presentation.objects.add.simple.VideoAdapter
import kotlinx.android.synthetic.main.fragment_object_complaint.*
import kotlinx.android.synthetic.main.include_toolbar.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import java.util.*
import kotlin.collections.ArrayList

class ObjectComplaintFragment : PhotoBaseDialogFragment(), ObjectComplaintFragmentView{

    companion object{

        val TAG = "ObjectComplaintFragment"

        private val BUNDLE_OBJECT_ID = "object_id"

        fun newInstance(objectId: Int? = null): ObjectComplaintFragment =
            ObjectComplaintFragment().apply {
                arguments = Bundle().apply {
                    objectId?.let { putInt(BUNDLE_OBJECT_ID, objectId) }
                }
            }
    }

    @InjectPresenter
    lateinit var presenter: ObjectComplaintPresenter

    @ProvidePresenter
    fun providePresenter(): ObjectComplaintPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_COMPLAINT_SCOPE)?.close()
        val scope = getKoin().getOrCreateScope(MainScope.OBJECT_COMPLAINT_SCOPE, named(MainScope.OBJECT_COMPLAINT_SCOPE))
        val objectId = arguments?.getInt(BUNDLE_OBJECT_ID)
        return scope.get { parametersOf(objectId) }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_object_complaint

    private val optionsAdapter = ComplaintOptionAdapter{ }

    private val videoAdapter = VideoAdapter(
        { presenter.onAddVideoItemClicked() },
        { presenter.onRemoveVideoItemClicked(it) }
    )

    private val photoAdapter = PhotoAdapter(
        { pickPhotoFromGalleryWithPermissionCheck(false) },
        {  }
    )

    override fun setUp(savedInstanceState: Bundle?) {
        initViewsListeners()
        initViews()
        initTitles()
        presenter.onFirstInit()
    }

    private fun initViews(){
        txtTitleToolbarMain?.text = getString(R.string.complaint_create)
        btnReadyToolbarMain?.visible(true)
        recyclerOptionsOC?.adapter = optionsAdapter
        recyclerVideoOC?.adapter = videoAdapter
        recyclerPhotoOC?.adapter = photoAdapter
    }

    private fun initTitles(){
        txtTextOC?.text = Html.fromHtml(getString(R.string.complaint_text), Html.FROM_HTML_MODE_LEGACY)
        edtSurnameOC?.setTitle(Html.fromHtml(getString(R.string.complaint_surname), Html.FROM_HTML_MODE_LEGACY))
        edtNameOC?.setTitle(Html.fromHtml(getString(R.string.complaint_name), Html.FROM_HTML_MODE_LEGACY))
        edtMiddleNameOC?.setTitle(Html.fromHtml(getString(R.string.complaint_middle_name), Html.FROM_HTML_MODE_LEGACY))
        edtIinOC?.setTitle(Html.fromHtml(getString(R.string.complaint_iin), Html.FROM_HTML_MODE_LEGACY))
        txtCityOC?.setTitle(Html.fromHtml(getString(R.string.complaint_city), Html.FROM_HTML_MODE_LEGACY))
        edtStreetOC?.setTitle(Html.fromHtml(getString(R.string.complaint_street), Html.FROM_HTML_MODE_LEGACY))
        edtHouseOC?.setTitle(Html.fromHtml(getString(R.string.complaint_house), Html.FROM_HTML_MODE_LEGACY))
        edtApartmentOC?.setTitle(getString(R.string.complaint_apartment))
        edtPhoneOC?.setTitle(Html.fromHtml(getString(R.string.complaint_phone), Html.FROM_HTML_MODE_LEGACY))
        txtAuthorityOC?.setTitle(Html.fromHtml(getString(R.string.complaint_authority), Html.FROM_HTML_MODE_LEGACY))
        txtTypeOC?.setTitle(Html.fromHtml(getString(R.string.complaint_type), Html.FROM_HTML_MODE_LEGACY))
        txtDateOC?.setTitle(Html.fromHtml(getString(R.string.complaint_date), Html.FROM_HTML_MODE_LEGACY))
        edtNameComplaintOC?.setTitle(Html.fromHtml(getString(R.string.complaint_name), Html.FROM_HTML_MODE_LEGACY))
        txtCityComplaintOC?.setTitle(Html.fromHtml(getString(R.string.complaint_city), Html.FROM_HTML_MODE_LEGACY))
        edtStreetComplaintOC?.setTitle(Html.fromHtml(getString(R.string.complaint_street), Html.FROM_HTML_MODE_LEGACY))
        edtHouseComplaintOC?.setTitle(Html.fromHtml(getString(R.string.complaint_house), Html.FROM_HTML_MODE_LEGACY))
        edtGoalComplaintOC?.setTitle(Html.fromHtml(getString(R.string.complaint_goal), Html.FROM_HTML_MODE_LEGACY))
        txtChooseOC?.text = Html.fromHtml(getString(R.string.complaint_choose), Html.FROM_HTML_MODE_LEGACY)
    }

    private fun initViewsListeners(){
        imgBackToolbarMain?.setOnClickListener { dismiss() }
        txtCityOC?.setOnClickListener { presenter.onCityBtnClicked() }
        txtCityComplaintOC?.setOnClickListener { presenter.onCityComplaintBtnClicked() }
        txtAuthorityOC?.setOnClickListener { presenter.onAuthorityBtnClicked() }
        txtTypeOC?.setOnClickListener { presenter.onTypeBtnClicked() }
        btnReadyToolbarMain?.setOnClickListener {
            if(isDataValid()) {
                if (edtIinOC.getValue().length == 12) {
                    presenter.onReadyBtnClicked(
                        name = edtNameOC.getValue(),
                        surname = edtSurnameOC.getValue(),
                        middleName = edtMiddleNameOC.getValue(),
                        iin = edtIinOC.getValue(),
                        street = edtStreetOC.getValue(),
                        house = edtHouseOC.getValue(),
                        apartment = edtApartmentOC.getValue(),
                        phone = edtPhoneOC.getValue(),
                        nameComplaint = edtNameComplaintOC.getValue(),
                        streetComplaint = edtStreetComplaintOC.getValue(),
                        houseComplaint = edtHouseComplaintOC.getValue(),
                        officeComplaint = edtOfficeComplaintOC.getValue(),
                        goalComplaint = edtGoalComplaintOC.getValue(),
                        comment = edtOtherComplaintOC.getValue(),
                        rememberData = checkboxRememberOC.isChecked,
                        isLifeThread = checkboxLifeThreadOC.isChecked
                    )
                }else{
                    showMessage(getString(R.string.complaint_wrong_iin))
                }
            }
        }
        txtDateOC?.setOnClickListener {
            context?.let {
                val picker = DatePickerDialog(
                    it,
                    datePickerListener,
                    Calendar.getInstance()[Calendar.YEAR],
                    Calendar.getInstance()[Calendar.MONTH],
                    Calendar.getInstance()[Calendar.DAY_OF_MONTH]
                )
                picker.show()
                picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
                picker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(it, R.color.colorPrimary))
            }
        }
    }

    private fun isDataValid(): Boolean{
        if (edtSurnameOC.isDataValid() &&
                edtNameOC.isDataValid() &&
                edtMiddleNameOC.isDataValid() &&
                edtIinOC.isDataValid() &&
                txtCityOC.isDataValid() &&
                edtStreetOC.isDataValid() &&
                edtHouseOC.isDataValid() &&
                edtPhoneOC.isDataValid() &&
                txtAuthorityOC.isDataValid() &&
                txtTypeOC.isDataValid() &&
                txtDateOC.isDataValid() &&
                edtNameComplaintOC.isDataValid() &&
                txtCityComplaintOC.isDataValid() &&
                edtStreetComplaintOC.isDataValid() &&
                edtHouseComplaintOC.isDataValid() &&
                edtGoalComplaintOC.isDataValid()){
            return true
        }
        return false
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_COMPLAINT_SCOPE)
        super.onDestroy()
    }

    override fun showOptionsData(dataList: List<Option>) {
        optionsAdapter.submitData(dataList)
    }

    override fun setImage(uri: Uri) {

    }

    override fun setImagePath(path: String) {

    }

    override fun setImagePaths(paths: List<String>) {
        presenter.addPickedImage(paths)
    }

    override fun setVideo(uri: Uri) {

    }

    override fun setVideoPath(path: String) {

    }

    override fun showVideoData(dataList: List<String>) {
        videoAdapter.submitData(dataList.toMutableList())
    }

    override fun showPhotoData(dataList: List<String>) {
        photoAdapter.submitData(dataList.toMutableList())
    }

    override fun openListDialogFragment(dataList: List<ListItem>) {
        activity?.supportFragmentManager?.let {
            ListDialogFragment.newInstance(ArrayList(dataList))
            { presenter.onListItemSelected(it) }.show(it, ListDialogFragment.TAG)
        }
    }

    override fun showCityTitle(title: String) {
        txtCityOC?.setValue(title)
    }

    override fun showCityComplaintTitle(title: String) {
        txtCityComplaintOC?.setValue(title)
    }

    override fun showAuthorityTitle(title: String) {
        txtAuthorityOC?.setValue(title)
    }

    override fun showTypeTitle(title: String) {
        txtTypeOC?.setValue(title)
    }

    override fun showDateTitle(date: String) {
        txtDateOC?.setValue(date)
    }

    override fun closeThisFragmentWithResult(docId: Int) {
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .sendBroadcast(
                    Intent(AppConstants.FILTER_DOWNLOAD_FILE).apply {
                        putExtra(
                            AppConstants.BUNDLE_DOC_ID,
                            docId
                        )
                    }
                )
        }
        dismiss()
    }

    override fun showOptions(show: Boolean) {
        divider4?.visible(show)
        txtChooseOC?.visible(show)
        recyclerOptionsOC?.visible(show)
        edtOtherComplaintOC?.visible(show)
        checkboxLifeThreadOC?.visible(show)
    }

    override fun showComplaintProgressBar(show: Boolean) {
        progressBarComplaint?.visible(show)
    }

    override fun showCreatedComplaintInfo(createComplaint: CreateComplaint) {
        createComplaint.complainant?.apply {
            edtSurnameOC.setValue(lastName)
            edtNameOC.setValue(firstName)
            edtMiddleNameOC.setValue(middleName)
            edtIinOC.setValue(iin)
            edtStreetOC.setValue(street)
            edtHouseOC.setValue(building)
            edtApartmentOC.setValue(apartment)
            edtPhoneOC.setValue(phone?.replace("(", "")
                ?.replace(")","")
                ?.replace("+","")
                ?.replace("-","")
                ?.replace(" ", ""))

        }
        createComplaint.content?.apply {
            txtTypeOC.setValue(type)
            txtDateOC.setValue(visitedAt)
            edtNameComplaintOC.setValue(objectName)
            edtStreetComplaintOC.setValue(street)
            edtHouseComplaintOC.setValue(building)
            edtOfficeComplaintOC.setValue(office)
            edtGoalComplaintOC.setValue(visitPurpose)
            checkboxLifeThreadOC.isChecked = threatToLife ?: false
        }
        checkboxRememberOC.isChecked = createComplaint.rememberPersonalData ?: false
    }

    private val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        presenter.onDatePicked(year, month, dayOfMonth)
    }
}