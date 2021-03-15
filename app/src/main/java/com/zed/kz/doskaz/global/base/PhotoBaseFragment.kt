package com.zed.kz.doskaz.global.base

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.esafirm.imagepicker.features.ImagePicker
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.utils.AppConstants
import permissions.dispatcher.*
import timber.log.Timber
import java.io.File

@RuntimePermissions
abstract class PhotoBaseFragment: BaseFragment(){

    private var cameraImageUri: Uri? = null
    private var currentType: Int = 0
    private var single: Boolean = true


    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun pickPhotoFromGallery(single: Boolean = true) {
        this.single = single
        currentType = PICK_IMAGE_GALLERY
        if (single){
            ImagePicker.create(this)
                .showCamera(false)
                .single()
                .start()
        }else{
            ImagePicker.create(this)
                .showCamera(false)
                .start()
        }
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun pickVideoFromGallery() {
        currentType = PICK_VIDEO_GALLERY
        ImagePicker.create(this)
            .onlyVideo(true)
            .single()
            .start()
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    fun pickPhotoFromCamera() {
        currentType = PICK_IMAGE_CAMERA
        context?.let { cont->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            context?.getExternalFilesDir(AppConstants.FOLDER_IMAGE)?.let {
                val photo = File(it.path, "${System.currentTimeMillis()}.png")

                cameraImageUri = FileProvider.getUriForFile(
                    cont,
                    "com.zed.kz.doskaz.provider",
                    photo
                )
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    cameraImageUri
                )
                startActivityForResult(intent, PICK_IMAGE_CAMERA)
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)


            when(currentType){
                PICK_IMAGE_GALLERY -> {
                    if (single){
                        setImagePath(image.path)
                    }else{
                        val images = ImagePicker.getImages(data)
                        val tempList: MutableList<String> = mutableListOf()
                        images.forEach { tempList.add(it.path) }
                        setImagePaths(tempList)
                    }
                }
                PICK_VIDEO_GALLERY -> {
                    setVideoPath(image.path)
                }
            }

        }
        when(currentType){
            PICK_IMAGE_CAMERA -> {
                cameraImageUri?.let {
                    setImage(it)
                    cameraImageUri = null
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForExternalStorage(request: PermissionRequest) {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("Permission")
                .setPositiveButton(android.R.string.yes) { _, _ -> request.proceed() }
                .setNegativeButton(android.R.string.no) { _, _ -> request.cancel() }
                .show()
        }
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDeniedForExternalStorage() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showNeverAskForExternalStorage() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    abstract fun setImage(uri: Uri)

    abstract fun setImagePath(path: String)

    abstract fun setImagePaths(paths: List<String>)

    abstract fun setVideo(uri: Uri)

    abstract fun setVideoPath(path: String)

    companion object {
        private const val PICK_IMAGE_CAMERA = 1
        private const val PICK_IMAGE_GALLERY = 2
        private const val PICK_VIDEO_GALLERY = 3
    }

}