package com.zed.kz.doskaz.main.presentation.main.contacts

import com.arellomobile.mvp.InjectViewState
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.entity.Feedback
import com.zed.kz.doskaz.global.presentation.BasePresenter
import com.zed.kz.doskaz.global.system.ResourceManager
import com.zed.kz.doskaz.main.data.interactor.AuthInteractor
import com.zed.kz.doskaz.main.data.interactor.ListInteractor

@InjectViewState
class ContactsPresenter(
    private val resourceManager: ResourceManager,
    private val authInteractor: AuthInteractor,
    private val listInteractor: ListInteractor
): BasePresenter<ContactsFragmentView>(){

    fun onFirstInit(){
        getRegional()
    }

    private fun getRegional(){
        listInteractor.getRegional()
            .subscribe(
                {
                    viewState?.showRegional(it)
                },
                {
                    it.printStackTrace()
                }
            ).connect()
    }

    fun sendFeedback(
        name: String,
        email: String,
        text: String
    ){
        val fillField = resourceManager.getString(R.string.fill_field)

        if (name.isEmpty()){
            viewState?.showMessage("$fillField ${resourceManager.getString(R.string.contacts_your_name)}")
            return
        }

        if (email.isEmpty()){
            viewState?.showMessage("$fillField ${resourceManager.getString(R.string.contacts_your_email)}")
            return
        }

        if (text.isEmpty()){
            viewState?.showMessage("$fillField ${resourceManager.getString(R.string.contacts_your_text)}")
            return
        }

        viewState?.showProgressBar(true)

        authInteractor.sendFeedback(
            Feedback(
                name = name,
                email = email,
                text = text
            )
        ).subscribe(
            {
                viewState?.showProgressBar(false)
                viewState?.showMessage(resourceManager.getString(R.string.contacts_sent))
                viewState?.clearContactsViews()
            },
            {
                viewState?.showProgressBar(false)
            }
        ).connect()
    }

}