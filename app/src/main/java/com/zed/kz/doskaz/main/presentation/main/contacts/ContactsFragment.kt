package com.zed.kz.doskaz.main.presentation.main.contacts

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Regional
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.visible
import com.zed.kz.doskaz.main.di.MainScope
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class ContactsFragment : BaseFragment(), ContactsFragmentView{

    companion object{

        val TAG = "ContactsFragment"

        fun newInstance(): ContactsFragment =
            ContactsFragment()
    }

    @InjectPresenter
    lateinit var presenter: ContactsPresenter

    @ProvidePresenter
    fun providePresenter(): ContactsPresenter {
        getKoin().getScopeOrNull(MainScope.CONTACTS_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.CONTACTS_SCOPE, named(MainScope.CONTACTS_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_contacts

    private val adapter = RegionalAdapter()

    override fun setUp(savedInstanceState: Bundle?) {
        imgBackToolbarMain?.setOnClickListener { activity?.onBackPressed() }
        txtTitleToolbarMain?.apply {
            visible(true)
            text = getString(R.string.contacts)
        }

        btnSendContacts?.setOnClickListener {
            if (isDataValid()){
                presenter.sendFeedback(
                    name = edtNameContacts?.getValue().toString(),
                    email = edtEmailContacts?.getValue().toString(),
                    text = edtTextContacts?.getValue().toString()
                )
            }
        }

        txt1Contacts?.setOnClickListener {
            val intent = Intent().apply {
                putExtra(Intent.EXTRA_EMAIL, getString(R.string.contacts_1))
                putExtra(Intent.EXTRA_SUBJECT, "Text")
            }
            context?.startActivity(Intent.createChooser(intent, "Send Email"))
        }
        txt2Contacts?.setOnClickListener { context?.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: ${getString(R.string.contacts_2)}"))) }

        recyclerRegionalContacts?.adapter = adapter

        presenter.onFirstInit()
    }

    private fun isDataValid(): Boolean{

        if (edtNameContacts.isDataValid(nestedContract, rootContacts) &&
            edtEmailContacts.isDataValid(nestedContract, rootContacts) &&
            edtTextContacts.isDataValid(nestedContract, rootContacts)){

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmailContacts.getValue()).matches()){
                Toast.makeText(context, getString(R.string.warning_wrong_email), Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        return false
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.CONTACTS_SCOPE)
        super.onDestroy()
    }

    override fun clearContactsViews() {
        edtNameContacts?.setValue("")
        edtEmailContacts?.setValue("")
        edtTextContacts?.setValue("")
    }

    override fun showRegional(dataList: List<Regional>) {
        adapter.submitData(dataList)
    }

}