package com.app.mndalakanm.ui.Home

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.mndalakanm.utils.MyService
import com.app.mndalakanm.utils.ScreenshotService
import com.app.mndalakanm.utils.SharedPref
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.ActivitySuperviseHomeBinding
import com.vilborgtower.user.utils.Constant


class SuperviseHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuperviseHomeBinding
    lateinit var navController: NavController
    lateinit var sharedPref: SharedPref
 lateinit var projectionManager :MediaProjectionManager
    private val REQUEST_SCREENSHOT = 59706
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_supervise_home)
        val host: NavHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_home_fragment_container)
                as NavHostFragment? ?: return
        navController = host.navController
        setupWithNavController(binding.navView, navController)
        sharedPref = SharedPref(this)
        binding. imgHeader .setOnClickListener {
           onBackPressed()
        }
        binding.tvLogo.text = sharedPref.getStringValue(Constant.CHILD_NAME)
        binding. imgMenu .setOnClickListener {
        //   navController.navigate(R.id.action_splash_to_menu_fragment)
            startwork()
        }

         projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
         startActivityForResult(projectionManager.createScreenCaptureIntent(),2)
        startService(Intent(applicationContext, MyService::class.java)
            .putExtra(ScreenshotService.EXTRA_RESULT_CODE, "resultCode")
            .putExtra(ScreenshotService.EXTRA_RESULT_INTENT, "data"))
    }

    private fun startwork() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            Log.e(TAG, "onActivityResult: requestCode"+ requestCode)
            Log.e(TAG, "onActivityResult:resultCode "+ resultCode)
            if (resultCode == Activity.RESULT_OK) {
                startService(Intent(applicationContext, ScreenshotService::class.java)
                    .putExtra(ScreenshotService.EXTRA_RESULT_CODE, resultCode)
                    .putExtra(ScreenshotService.EXTRA_RESULT_INTENT, data))

            }
        }
    }




}