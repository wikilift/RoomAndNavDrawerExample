package com.wikilift.tfg.ui.maps

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View

import android.webkit.WebView
import android.webkit.WebViewClient
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.IOnBackPressed
import com.wikilift.tfg.databinding.FragmentNearVetsWebViewBinding


class NearVetsWebView : Fragment(R.layout.fragment_near_vets_web_view),IOnBackPressed {
    private lateinit var binding:FragmentNearVetsWebViewBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentNearVetsWebViewBinding.bind(view)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        binding.webView.loadUrl("https://www.covb.cat/es/servicios-a-particulares/buscador-de-centros-veterinarios/")
    }

    override fun onBackPressed(): Boolean= true
}

