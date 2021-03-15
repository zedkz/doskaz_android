package com.zed.kz.doskaz.main.presentation.main.objects.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zed.kz.doskaz.R
import kotlinx.android.synthetic.main.dialog_fragment_object_review.*

class ObjectReviewDialogFragment(val onYesBtnClickListener: () -> Unit): DialogFragment(){

    companion object{

        val TAG = "ObjectReviewDialogFragment"

        fun newInstance(onYesBtnClickListener: () -> Unit): ObjectReviewDialogFragment =
            ObjectReviewDialogFragment(onYesBtnClickListener)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.dialog_fragment_object_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnYesOVDR?.setOnClickListener {
            onYesBtnClickListener.invoke()
            dismiss()
        }
        btnCancelOVDR?.setOnClickListener {
            dismiss()
        }
    }
}