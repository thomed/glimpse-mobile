package com.glimpse.glimpse.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.glimpse.glimpse.R
import com.google.android.material.textfield.TextInputEditText

class SiteUrlDialog : DialogFragment() {
    private lateinit var okBtn : Button
    private lateinit var cancelBtn : Button
    private lateinit var textBox : TextInputEditText
    private var urlString : String = ""

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
        initializeClickHandlers()
    }

    private fun initializeClickHandlers() {
        okBtn?.setOnClickListener {
            urlString = textBox?.text.toString()
            Log.d("SITE_DIALOG", "Has entered text: $urlString")
        }
    }

}