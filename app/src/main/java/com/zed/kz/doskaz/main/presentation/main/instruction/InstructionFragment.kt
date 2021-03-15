package com.zed.kz.doskaz.main.presentation.main.instruction

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class InstructionFragment : BaseFragment(), InstructionFragmentView{

    companion object{

        val TAG = "InstructionFragment"

        fun newInstance(): InstructionFragment =
            InstructionFragment()
    }

    @InjectPresenter
    lateinit var presenter: InstructionPresenter

    @ProvidePresenter
    fun providePresenter(): InstructionPresenter {
        getKoin().getScopeOrNull(MainScope.INSTRUCTION_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.INSTRUCTION_SCOPE, named(MainScope.INSTRUCTION_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_instruction

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.instruction)
        }
    }


    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.INSTRUCTION_SCOPE)
        super.onDestroy()
    }
}