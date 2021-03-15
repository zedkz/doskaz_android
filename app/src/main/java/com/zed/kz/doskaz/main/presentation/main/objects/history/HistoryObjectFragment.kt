package com.zed.kz.doskaz.main.presentation.main.objects.history

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.object_info.History
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_object_history.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class HistoryObjectFragment : BaseFragment(), HistoryObjectFragmentView{

    companion object{

        val TAG = "HistoryObjectFragment"

        fun newInstance(): HistoryObjectFragment =
            HistoryObjectFragment()
    }

    @InjectPresenter
    lateinit var objectPresenter: HistoryObjectPresenter

    @ProvidePresenter
    fun providePresenter(): HistoryObjectPresenter {
        getKoin().getScopeOrNull(MainScope.HISTORY_OBJECT_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.HISTORY_OBJECT_SCOPE, named(MainScope.HISTORY_OBJECT_SCOPE)).get()
    }

    private val adapter = ObjectHistoryAdapter()

    override val layoutRes: Int
        get() = R.layout.fragment_object_history

    override fun setUp(savedInstanceState: Bundle?) {
        recyclerObjectHistory?.adapter = adapter
        objectPresenter.onFirstInit()
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.HISTORY_OBJECT_SCOPE)
        super.onDestroy()
    }

    override fun showDataList(dataList: List<History>) {
        adapter.submitData(dataList)
    }

}