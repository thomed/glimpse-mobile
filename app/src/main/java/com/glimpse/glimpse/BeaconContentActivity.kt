package com.glimpse.glimpse

import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BeaconContentActivity : AppCompatActivity() {

    lateinit var titleText : String
    lateinit var content : String
    lateinit var contentType : String
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beacon_content)


        titleText = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        contentType = intent.getStringExtra("content_type")
        webView = findViewById(R.id.contentWebView)

        this.title = titleText

        if (contentType == "text") {
            var template = getString(R.string.text_content_html)
            var defaultStyle = getString(R.string.default_styles)
            webView.loadDataWithBaseURL("", template.format(defaultStyle, content), "text/html", "UTF-8", "")
        } else {
            var template = getString(R.string.formatted_content_html)
            webView.loadDataWithBaseURL("", template.format(content), "text/html", "UTF-8", "")

        }

    }
}