package com.funwords.worldssports;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.push.YandexMetricaPush;


public class AppMetrica extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("e9a4f53a-cd97-4312-b065-2a825527fdf2").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        Log.d("YandexMetrica", "YandexMetricaYandexMetricaYandexMetrica");
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);


        YandexMetricaPush.init(getApplicationContext());

    }
}