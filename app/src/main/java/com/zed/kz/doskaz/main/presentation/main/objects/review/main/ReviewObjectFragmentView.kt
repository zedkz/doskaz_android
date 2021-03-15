package com.zed.kz.doskaz.main.presentation.main.objects.review.main

import com.zed.kz.doskaz.entity.object_info.Review
import com.zed.kz.doskaz.global.base.BaseMvpView

interface ReviewObjectFragmentView : BaseMvpView{

    fun showDataList(dataList: List<Review>)

}