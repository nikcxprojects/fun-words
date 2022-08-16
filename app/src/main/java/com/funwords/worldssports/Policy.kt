package com.funwords.worldssports

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.Response
import okhttp3.Call
import okhttp3.Callback
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


//https://stackoverflow.com/questions/29488208/replacing-html-text-on-an-android-webview-template-with-javascript

//https://translated.turbopages.org/proxy_u/en-ru.ru.cfd314a1-62a1047f-87be45c3-74722d776562/https/stackoverflow.com/questions/58383355/android-webview-change-elements-with-javascript-after-page-finished-loading

//https://stackoverflow.com/questions/8938119/changing-html-in-a-webview-programmatically

class Policy : AppCompatActivity(){

    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val web: WebView = findViewById(R.id.web_browser)
        val web_no_connect: LinearLayout = findViewById(R.id.no_connect)
        var url_adress: String


        val data = intent.getStringExtra("polisy")

        //проверка инета
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                if (isAvailable) {
                    web.visibility = View.VISIBLE
                    web_no_connect.visibility = View.GONE
                } else {
                    web.visibility = View.GONE
                    web_no_connect.visibility = View.VISIBLE
                }
            }
        }


        val url = data.toString()

        val serverURL = "http://185.253.45.16/lander/policy2/index.html"

        //web.loadDataWithBaseURL(null, data!!, "text/html", "utf-8", null);
        web.loadUrl(serverURL)
        web.settings.javaScriptEnabled = true
        web.canGoBack()
        web.settings.loadsImagesAutomatically = true
        //добавление кэша и куки
        web.settings.setAppCacheEnabled(false)
        web.settings.databaseEnabled = true
        web.settings.domStorageEnabled = true; // Открываем кеш DOM
        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

        //взаимодествие до прогрузки страницы в вэбвью
        web.webViewClient = object : WebViewClient() {

//            @SuppressLint("CommitPrefEdits")
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?,
//            ): Boolean {
//                view?.loadUrl(request?.url.toString())
//
//                //сохранение текущей ссылки
//                val editor = sharedPreference.edit()
//                url_adress = request?.url.toString()
//                editor.apply {
//                    putString("url_address", url_adress)
//                }.apply()
//
//                //сохранение ссылки
//                //Toast.makeText(this@MainActivity, sharedPreference.getString("url_address", ""), Toast.LENGTH_SHORT).show()
//
//                return true
//            }

            //начало заргузки страницы
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")


                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")
                web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'com.funwords.worldssports')})()")


                //web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.getElementById('replace_description_3').replace(' {id}', 'Leon C')})()")

//                val textToChange = "Leon C"
//
//                val javaScript =
//                    ("javascript: var element = document.getElementById('replace_description_1');"
//                            + "element.innerHTML = '{id}'" + textToChange + "';")
//                web.loadUrl(javaScript)

                //web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace('{id}', 'Leon C')})()");
            }

            //завершение заргузки страницы
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                //web.loadUrl("javascript:(function(){document.getElementById('wp-submit').click();})();")
                //Toast.makeText(applicationContext, "Загрузка страницы завершена", Toast.LENGTH_SHORT).show()
//
//                if (swipeRefresh.isRefreshing) {
//                    swipeRefresh.isRefreshing = false
//                }

//                val textToChange = "NEW TEXT"
//                val javaScript =
//                    ("javascript: var element = document.getElementById('REPLACE_TITLE');"
//                            + "element.innerHTML = '" + textToChange + "';")
//                view!!.loadUrl(javaScript)

//                if (view != null) {
//                    view.loadUrl("javascript:clickFunction(){})()")
//                };
                //Leon C
                //web.loadUrl("javascript:(function(){document.body.innerHTML = document.body.innerHTML.replace(' {id}', 'com.lionc.app')})()");


            }

        }

    }

    override fun onBackPressed() {
        val web_browser: WebView = findViewById(R.id.web_browser)
        if (web_browser.canGoBack())
            web_browser.goBack()
        else
            finish()
    }

}