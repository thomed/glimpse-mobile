package com.glimpse.glimpse.ui

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.glimpse.glimpse.R
import com.google.android.material.textfield.TextInputEditText

/**
 * TODO be able to create a dialog with an error message and the faulty url populating the textbox
 * for cases where the submitted url is found to be an invalid Glimpse server
 */
class SiteUrlDialog(var dialogCreator : Fragment) : DialogFragment() {
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var textBox : TextInputEditText
    var positive = false // possibly hacky way of communicating clicked ok/cancel
    var urlString : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_site_url_dialog, container)
    }

    override fun onStart() {
        super.onStart()
        okBtn = requireView().findViewById(R.id.siteDialogOkBtn)
        textBox = requireView().findViewById(R.id.siteDialogTextBox)
        cancelBtn = requireView().findViewById(R.id.siteDialogCancelBtn)
        urlString = ""
        positive = false
        initializeClickHandlers()
    }

    private fun initializeClickHandlers() {
        okBtn?.setOnClickListener {
            urlString = textBox?.text.toString()
            Log.d("SITE_DIALOG", "Has entered text: $urlString")
            positive = true
            dismiss()
        }
        cancelBtn?.setOnClickListener {
//            dialog?.cancel()
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (dialogCreator is DialogInterface.OnDismissListener) {
            Log.d("SITE_DIALOG", "Dialog dismissed. Notifying parent activity.")
            (dialogCreator as DialogInterface.OnDismissListener).onDismiss(dialog)
        }
    }
}
