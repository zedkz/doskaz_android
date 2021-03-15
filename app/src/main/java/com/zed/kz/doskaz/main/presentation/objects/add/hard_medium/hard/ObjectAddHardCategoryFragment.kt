package com.zed.kz.doskaz.main.presentation.objects.add.hard_medium.hard

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.main.di.MainScope
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ObjectAddHardCategoryFragment : BaseFragment(), ObjectAddHardCategoryFragmentView{

    companion object{

        val TAG = "ObjectAddHardCategoryFragment"

        fun newInstance(): ObjectAddHardCategoryFragment =
            ObjectAddHardCategoryFragment()
    }

    @InjectPresenter
    lateinit var presenter: ObjectAddHardCategoryPresenter

    @ProvidePresenter
    fun providePresenter(): ObjectAddHardCategoryPresenter {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_HARD_CATEGORY_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.OBJECT_ADD_HARD_CATEGORY_SCOPE, named(MainScope.OBJECT_ADD_HARD_CATEGORY_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_bonus

    override fun setUp(savedInstanceState: Bundle?) {
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.OBJECT_ADD_HARD_CATEGORY_SCOPE)
        super.onDestroy()
    }

}