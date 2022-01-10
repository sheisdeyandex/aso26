package com.vavada.aso26
import androidx.multidex.MultiDexApplication
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.vavada.aso26.interfaces.ApiInterface
import com.vavada.aso26.models.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class app : MultiDexApplication() {
     lateinit var config:Config
    override fun onCreate() {
        super.onCreate()
        val apiInterface = ApiInterface.create().getConfig()
        apiInterface.enqueue( object : Callback<Config> {
            override fun onResponse(call: Call<Config>?, response: Response<Config>?) {

                if(response?.body() != null)
                    config = response.body()!!
    AppsFlyerLib.getInstance().init(config.Appsflyer, null, applicationContext);

                AppsFlyerLib.getInstance().start(applicationContext)
                OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

                // OneSignal Initialization
                OneSignal.initWithContext(applicationContext);
                response?.body()?.let { OneSignal.setAppId(config.Onesignal) }
            }

            override fun onFailure(call: Call<Config>?, t: Throwable?) {

            }
        })
    }

}