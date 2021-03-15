package com.zed.kz.doskaz.main.presentation.profile.my.complaint

import com.zed.kz.doskaz.entity.Complaint
import com.zed.kz.doskaz.global.base.BaseMvpView

interface MyComplaintFragmentView : BaseMvpView{

    fun showComplaints(dataList: List<Complaint>)

}