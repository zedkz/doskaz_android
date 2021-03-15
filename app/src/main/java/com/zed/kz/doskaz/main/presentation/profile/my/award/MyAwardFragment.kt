package com.zed.kz.doskaz.main.presentation.profile.my.award

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Award
import com.zed.kz.doskaz.entity.Event
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_my_award.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class MyAwardFragment : BaseFragment(), MyAwardFragmentView {

    companion object{

        val TAG = "MyAwardFragment"

        fun newInstance(): MyAwardFragment =
            MyAwardFragment()
    }

    @InjectPresenter
    lateinit var presenter: MyAwardPresenter

    @ProvidePresenter
    fun providePresenter(): MyAwardPresenter {
        getKoin().getScopeOrNull(MainScope.MY_AWARD_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.MY_AWARD_SCOPE, named(MainScope.MY_AWARD_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_my_award

    private val awardAdapter = MyAwardAdapter()
    private val eventAdapter = MyEventAdapter()

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerMyAward.adapter = awardAdapter
        recyclerEventMyAward.adapter = eventAdapter
        presenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.MY_AWARD_SCOPE)
        super.onDestroy()
    }

    override fun showAwards(dataList: List<Award>) {
        awardAdapter.submitData(dataList)
    }

    override fun showEvents(dataList: List<Event>) {
        eventAdapter.submitData(dataList)
    }
}