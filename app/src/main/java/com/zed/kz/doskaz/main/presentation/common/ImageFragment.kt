package com.zed.kz.doskaz.main.presentation.common

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davemorrissey.labs.subscaleview.ImageSource
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseDialogFragment
import com.zed.kz.doskaz.global.di.ServiceProperties
import com.zed.kz.doskaz.global.extension.visible
import kotlinx.android.synthetic.main.fragment_image.*
import kotlinx.android.synthetic.main.include_toolbar_main.*

class ImageFragment : BaseDialogFragment(){

    companion object{

        val TAG = "ImageFragment"

        private val BUNDLE_IMAGE_PATH = "image_path"

        fun getInstance(imagePath: String): ImageFragment =
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_IMAGE_PATH, imagePath)
                }
            }

    }

    override val layoutRes: Int
        get() = R.layout.fragment_image

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setUp(savedInstanceState: Bundle?) {
        progress?.visible(true)
        context?.let {
            Glide.with(it)
                .asBitmap()
                .load("${ServiceProperties.SERVER_URL}${arguments?.getString(BUNDLE_IMAGE_PATH)}")
                .addListener(object : RequestListener<Bitmap>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        activity?.runOnUiThread {
                            progress?.visible(false)
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        activity?.runOnUiThread {
                            progress?.visible(false)
                            resource?.let { ssimgImage?.setImage(ImageSource.bitmap(it)) }
                        }
                        return true
                    }
                })
                .submit()

        }

        imgBackToolbarMain?.setOnClickListener { dismiss() }
    }

}