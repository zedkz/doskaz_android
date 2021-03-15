package com.zed.kz.doskaz.main.presentation.main.objects.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zed.kz.doskaz.R
import kotlinx.android.synthetic.main.dialog_fragment_object_verification.*
import kotlinx.android.synthetic.main.fragment_object_description.*
import timber.log.Timber

class ObjectVerificationDialogFragment(val onYesBtnClickListener: () -> Unit, val onNoBtnClickListener: () -> Unit): DialogFragment(){

    companion object{

        val TAG = "ObjectVerificationDialogFragment"

        private val BUNDLE_OBJECT_NAME = "object_name"

        fun newInstance(objectName: String, onYesBtnClickListener: () -> Unit, onNoBtnClickListener: () -> Unit): ObjectVerificationDialogFragment =
            ObjectVerificationDialogFragment(onYesBtnClickListener, onNoBtnClickListener).apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_OBJECT_NAME, objectName)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.dialog_fragment_object_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val objectName = arguments?.getString(BUNDLE_OBJECT_NAME)
        txtDescriptionOVDF?.text = getString(R.string.om_verification_text, objectName)
        btnYesOVDF?.setOnClickListener {
            onYesBtnClickListener.invoke()
            dismiss()
        }
        btnNoOVDF?.setOnClickListener {
            onNoBtnClickListener.invoke()
            dismiss()
        }
    }
}