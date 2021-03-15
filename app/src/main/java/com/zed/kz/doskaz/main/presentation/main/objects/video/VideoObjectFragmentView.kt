package com.zed.kz.doskaz.main.presentation.main.objects.video

import com.zed.kz.doskaz.entity.object_info.Video
import com.zed.kz.doskaz.global.base.BaseMvpView

interface VideoObjectFragmentView : BaseMvpView{

    fun showDataList(dataList: List<Video>)

}