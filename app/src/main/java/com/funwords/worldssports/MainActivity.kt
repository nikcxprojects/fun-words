package com.funwords.worldssports

//v 1.0 - WebView с Fire Base
//v 1.1 - WebView с API Keitaro

//https://www.youtube.com/watch?v=GmpD2DqQYVk

//https://www.youtube.com/watch?v=ywRkvmNLTXE

//https://www.youtube.com/watch?v=IsEI1byDd4Q

//https://github.com/johncodeos-blog/CheckInternetAndroidExample/blob/master/app/src/main/res/layout/activity_main.xml

//https://www.programcreek.com/java-api-examples/?class=android.telephony.TelephonyManager&method=SIM_STATE_ABSENT

//https://stackoverflow.com/questions/14389349/android-get-current-locale-not-default

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {

    private var uploadMessage: ValueCallback<Uri>? = null
    private var uploadMessageAboveL: ValueCallback<Array<Uri>>? = null

    private val networkMonitor = NetworkMonitorUtil(this)

    //var sPref: SharedPreferences? = null

    var prefs: SharedPreferences? = null


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled", "HardwareIds", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val web: WebView = findViewById(R.id.web_browser)

        val web_no_connect: LinearLayout = findViewById(R.id.no_connect)

        var url_adress: String


        //запрос на резрешение приложения к фото и мультимедиа на устойстве
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_DENIED
//            ) {
//                Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it")
//                val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                requestPermissions(permissions, 1)
//            }
//        }

        //проверка инета
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                if (isAvailable) {
                    //Toast.makeText(applicationContext, "Есть инет", Toast.LENGTH_SHORT).show()
                    web.visibility = VISIBLE
                    web_no_connect.visibility = GONE
                } else {
                    //Toast.makeText(applicationContext, "Нет инета", Toast.LENGTH_SHORT).show()
                    web.visibility = GONE
                    web_no_connect.visibility = VISIBLE
                }
            }
        }


        //загрузка url
        val sharedPreference = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        val savedString = sharedPreference.getString("url_address", "")

        url_adress = savedString.toString()
//        web.loadUrl(url_adress)

        web.settings.javaScriptEnabled = true
        web.canGoBack()
        web.settings.loadsImagesAutomatically = true
        //добавление кэша и куки
        web.settings.setAppCacheEnabled(false)
        web.settings.databaseEnabled = true
        web.settings.domStorageEnabled = true; // Открываем кеш DOM
        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

//        val savedString = sharedPreference.getString("url","https://www.google.com/")

        val data = intent.getStringExtra("polisy")//sharedPreference.getString("html_polisy", "non")


        val token = sharedPreference.getString("token","")
        val offer_id = sharedPreference.getString("offer_id","")

        if (token==""){
            val i = Intent(this, PrivacyPolicy::class.java)
            web.loadDataWithBaseURL(null, data!!, "text/html", "utf-8", null);
            i.putExtra("polisy", data)
            startActivity(i)
            finish()
        } else {
            web.loadUrl("https://pokersom.ru/PWxqb1/?_lp=1&_token=$token&offer_id=\"$offer_id")
        }

        //взаимодествие после прогрузки страницы в вэбвью
        web.webChromeClient = object : WebChromeClient() {

            //For Android  >= 4.1
            fun openFileChooser(
                valueCallback: ValueCallback<Uri>,
                acceptType: String,
                capture: String,
            ) {
                uploadMessage = valueCallback
                openImageChooserActivity()
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                // Update the progress bar with page loading progress
                //progressBar.progress = newProgress
                val progressBar:ProgressBar = findViewById(R.id.progressBar)

                if (newProgress == 100) {
                    progressBar.visibility = GONE
                } else {
                    progressBar.visibility = VISIBLE
                    progressBar.progress = newProgress;
                }

            }

            // For Android >= 5.0
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: WebChromeClient.FileChooserParams,
            ): Boolean {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_DENIED
//                    ) {
//                        Log.d("permission", "permission denied to WRITE_EXTERNAL_STORAGE - requesting it")
//                        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        requestPermissions(permissions, 1)
//                    } else {
//                        uploadMessageAboveL = filePathCallback
//                        openImageChooserActivity()
//                    }
//                }
                uploadMessageAboveL = filePathCallback
                openImageChooserActivity()
                return true
            }
        }

        //обновлялка
        val swipeRefresh:SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isEnabled = web.scrollY == 0
        }

        //взаимодествие до прогрузки страницы в вэбвью
        web.webViewClient = object : WebViewClient() {

            @SuppressLint("CommitPrefEdits")
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                view?.loadUrl(request?.url.toString())

                //сохранение текущей ссылки
                val editor = sharedPreference.edit()
                url_adress = request?.url.toString()
                editor.apply {
                    putString("url_address", url_adress)
                }.apply()

                //сохранение ссылки
                //Toast.makeText(this@MainActivity, sharedPreference.getString("url_address", ""), Toast.LENGTH_SHORT).show()

                return true
            }

            //начало заргузки страницы
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            //завершение заргузки страницы
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                if (swipeRefresh.isRefreshing) {
                    swipeRefresh.isRefreshing = false
                }

            }

        }

//      смена цвета при прогрузке
        swipeRefresh.setColorSchemeResources(android.R.color.holo_green_light,
            android.R.color.holo_blue_light)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }


    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//    }

    //чек проверка инета
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)

        return (capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET))

    }


//    private fun saveUrl(url: String?) {
//        val sp = PreferenceManager.getDefaultSharedPreferences(this)
//        val editor = sp.edit()
//        editor.putString("SAVED_URL", url)
//        editor.apply()
//    }

    private fun openImageChooserActivity() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.ECLAIR_MR1)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return
            val result = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data)
            } else if (uploadMessage != null) {
                uploadMessage!!.onReceiveValue(result)
                uploadMessage = null
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onActivityResultAboveL(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return
        var results: Array<Uri>? = null
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val dataString = intent.dataString
                val clipData = intent.clipData
                if (clipData != null) {
                    results = Array(clipData.itemCount) { i ->
                        clipData.getItemAt(i).uri
                    }
                }
                if (dataString != null)
                    results = arrayOf(Uri.parse(dataString))
            }
        }
        uploadMessageAboveL!!.onReceiveValue(results)
        uploadMessageAboveL = null
    }

    companion object {
        private val FILE_CHOOSER_RESULT_CODE = 10000
    }

    // onBackPressed() - кнопка назад системная
    //canGoBack() - проверка, если можно назад, то назад, иначе - закрыть
    override fun onBackPressed() {
        val web_browser: WebView = findViewById(R.id.web_browser)
        if (web_browser.canGoBack())
            web_browser.goBack()
        else
            finish()
    }

}