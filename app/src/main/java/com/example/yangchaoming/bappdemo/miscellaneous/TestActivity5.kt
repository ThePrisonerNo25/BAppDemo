package com.example.yangchaoming.bappdemo.miscellaneous

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.acitity_test5.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.CookieManager


class TestActivity5 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitity_test5)

        webview.webViewClient = MebViewClient()
        val cookieString = "bapp_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODY0MjAzNzUzODcsInBheWxvYWQiOiJ7XCJ1c2VySWRcIjpcIjE0Y2FjNTk1NjYzYzRmMGFhODdiNjM0MzYzZTUyN2FlXCIsXCJtZXJjaGFudElkXCI6XCIyZWU1OTdmMzc2ZDE0MTc4YWI5ZDgzMTMyYmUzZThiOVwiLFwic2hvcElkXCI6XCI2NzdiMzc0YjFhOGU0NDhjOGVlZTk4ZjBlOTMxZWRhNVwiLFwiZGVwYXJ0bWVudElkXCI6XCJcIixcInN1cGVyQWRtaW5cIjpmYWxzZSxcImJlbG9uZ1Nob3BJZFwiOm51bGwsXCJpZFwiOm51bGwsXCJwb3N0SWRcIjowLFwicG9zdENsYXNzaWZpY2F0aW9uXCI6MCxcInJvbGVJZFwiOlwiMTA5ZTZjZjNjYzllNGQ0Zjg1NDY4ZTM3NDY2M2YwZWJcIixcImNsYXNzaWZpY2F0aW9uXCI6MCxcImxvZ2luQnlNb2JpbGVcIjpmYWxzZSxcImNoaWVmXCI6ZmFsc2V9In0.EjkgUa3FUB9u7MlIRQ0gP3G02QgQD2x0EWzkfD31Vao; path=/"
        CookieManager.getInstance().setCookie("https://h5app-qa-club.ejoyst.com", cookieString)
        webview.loadUrl("https://h5app-qa-club.ejoyst.com/#/games");
        webview.settings.javaScriptEnabled=true
    }

    private  class MebViewClient : WebViewClient(){
        override fun onLoadResource(view: WebView?, url: String?) {
            Log.e("onLoadResource", "onLoadResource: $url");
            super.onLoadResource(view, url)
            
        }
    }
}