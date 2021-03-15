package com.zed.kz.doskaz.main.presentation.main.objects.video

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.Video
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_object_video.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class VideoObjectFragment : BaseFragment(), VideoObjectFragmentView{

    companion object{

        val TAG = "VideoObjectFragment"

        fun newInstance(): VideoObjectFragment =
            VideoObjectFragment()
    }

    @InjectPresenter
    lateinit var objectPresenter: VideoObjectPresenter

    @ProvidePresenter
    fun providePresenter(): VideoObjectPresenter {
        getKoin().getScopeOrNull(MainScope.VIDEO_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.VIDEO_OBJECT_SCOPE, named(MainScope.VIDEO_OBJECT_SCOPE)).get()
    }

    private val adapter = ObjectVideoAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_object_video

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerObjectVideo?.adapter = adapter
        objectPresenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.VIDEO_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showDataList(dataList: List<Video>) {
        adapter.submitData(dataList)
    }

}