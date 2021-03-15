package com.zed.kz.doskaz.main.presentation.activity

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.service.Endpoints
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.LocalStorage
import timber.log.Timber


@InjectViewState
class MainActivityPresenter(
    private val context: Context
): BasePresenter<MainActivityView>(){

    private lateinit var downloadManager: DownloadManager
    private var lastDownload = -1L
    private var FILE_NAME = "Complaint.docx"

    fun onFirstInit(){
        if (!LocalStorage.isLangChose()) {
            viewState.openChooseLanguageFragment()
        } else if (LocalStorage.isFirstTimeLaunched()){
            viewState?.openWelcomeFragment()
        }else {
            if (LocalStorage.getCurrentDisabilityCategory() == null){
                viewState?.openDisabilityCategoryFragment()
            }else{
                viewState?.openHomeFragment()
            }
        }

        setUpDownloadManager()
    }

    private fun setUpDownloadManager(){
        downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        context.registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        context.registerReceiver(
            onNotificationClick,
            IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        )
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(
                onDownloadFile,
                IntentFilter(AppConstants.FILTER_DOWNLOAD_FILE)
            )
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(
                onObjectCreated,
                IntentFilter(AppConstants.FILTER_OBJECT_CREATED)
            )
    }

    override fun onDestroy() {
        context.apply {
            unregisterReceiver(onDownloadFile)
            unregisterReceiver(onNotificationClick)
            unregisterReceiver(onComplete)
            unregisterReceiver(onObjectCreated)
        }
        super.onDestroy()
    }

    fun startDownload(docId: Int){
        val uri: Uri = Uri.parse(String.format(Endpoints.DOWNLOAD_FILE, docId))

        Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .mkdirs()

        lastDownload = downloadManager.enqueue(
            DownloadManager.Request(uri)
                .setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI or
                            DownloadManager.Request.NETWORK_MOBILE
                )
                .addRequestHeader("Authorization", "Bearer ${LocalStorage.getAccessToken()}")
                .setAllowedOverRoaming(false)
                .setTitle(context.getString(R.string.app_name))
                .setDescription(context.getString(R.string.complaint_file_downloading))
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    FILE_NAME
                )
        )
    }

    private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            viewState?.showLongMessage(context.getString(R.string.complaint_file_downloaded, FILE_NAME))
        }
    }

    private val onNotificationClick: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {

        }
    }

    private val onDownloadFile = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.extras
                ?.getInt(AppConstants.BUNDLE_DOC_ID)
                ?.let {
                    if (intent.extras?.getBoolean(AppConstants.BUNDLE_JUST_DOWNLOAD) ?: false)
                        startDownload(it)
                    else
                        viewState?.showDownloadAlertDialog(it)
                }
        }
    }

    private val onObjectCreated = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            viewState?.showObjectCreatedAlertDialog()
        }
    }

}