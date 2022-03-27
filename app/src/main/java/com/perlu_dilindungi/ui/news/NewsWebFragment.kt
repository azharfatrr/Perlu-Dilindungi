package com.perlu_dilindungi.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.decode.DataSource
import com.perlu_dilindungi.R
import com.perlu_dilindungi.config.Tag
import com.perlu_dilindungi.databinding.FragmentNewsWebBinding

import com.google.android.material.bottomnavigation.BottomNavigationView




class NewsWebFragment : Fragment() {

    companion object {
        var LINK = "link"
    }

    private lateinit var link: String

    // Binding attribute
    private var _binding: FragmentNewsWebBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the arguments from navigation.
        arguments?.let {
            link = it.getString(LINK).toString()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate and binding.
        _binding = FragmentNewsWebBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Bind the webView.
        val webView = binding.newsWebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Show the webView.
        webView.loadUrl(link)

        return binding.root
    }


}