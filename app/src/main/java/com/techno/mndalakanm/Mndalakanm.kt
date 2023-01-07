package com.techno.mndalakanm

import android.R
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import com.cityoneprovider.retrofit.ProviderInterface
import com.vilborgtower.user.utils.PrefManager
import com.vilborgtower.user.utils.Utils
import timber.log.Timber
import java.util.*


class Mndalakanm : Application() {
    var manager: PrefManager? = null
    var utils: Utils? = null
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        manager = PrefManager(applicationContext)
        utils = Utils(applicationContext)
        context = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //to disable dark mode

    }
    companion object {
        var apiInterface: ProviderInterface? = null
        var context: Context? = null
      /*  fun loadInterface(): ProviderInterface? {
            if (apiInterface == null) {
                apiInterface = ApiBuilder.getClient(context)?.create(APIInterface::class.java)
            }
            return apiInterface
        }*/
    }


}