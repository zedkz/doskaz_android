package com.zed.kz.doskaz.main.presentation.main.objects.photo

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Photo
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.common.ImageFragment
import kotlinx.android.synthetic.main.fragment_object_photo.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class PhotoObjectFragment : BaseFragment(), PhotoObjectFragmentView{

    companion object{

        val TAG = "PhotoObjectFragment"

        fun newInstance(): PhotoObjectFragment =
            PhotoObjectFragment()
    }

    @InjectPresenter
    lateinit var presenter: PhotoObjectPresenter

    @ProvidePresenter
    fun providePresenter(): PhotoObjectPresenter {
        getKoin().getScopeOrNull(MainScope.PHOTO_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.PHOTO_OBJECT_SCOPE, named(MainScope.PHOTO_OBJECT_SCOPE)).get()
    }

    private val photoAdapter = ObjectPhotoAdapter{ presenter.onPhotoItemClicked(it) }

    override val layoutRes: Int
        get() = R.layout.fragment_object_photo

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerPhotoObject?.adapter = photoAdapter
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.PHOTO_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showPhotos(photos: List<Photo>) {
        photoAdapter.submitData(photos)
    }

    override fun openImageFragment(path: String) {
        activity?.supportFragmentManager?.let {
            ImageFragment.getInstance(path)
                .show(
                    it,
                    ImageFragment.TAG
                )
        }
    }
}