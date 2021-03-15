package com.zed.kz.doskaz.main.presentation.web_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.zed.kz.doskaz.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment(){

    companion object{

        val TAG = "WebViewFragment"

        private val BUNDLE_CONTENT = "content"

        fun newInstance(content: String): WebViewFragment =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(BUNDLE_CONTENT, content)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = chromeClient
        val content = arguments?.getString(BUNDLE_CONTENT)

        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)

    }

    val chromeClient = object : WebChromeClient(){


    }
}