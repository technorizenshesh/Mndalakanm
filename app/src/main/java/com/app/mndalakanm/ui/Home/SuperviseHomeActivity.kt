package com.app.mndalakanm.ui.Home

import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.mndalakanm.utils.ForegroundService
import com.app.mndalakanm.utils.ScreenshotServiceOld
import com.app.mndalakanm.utils.SharedPref
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.ActivitySuperviseHomeBinding
import com.vilborgtower.user.utils.Constant


class SuperviseHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuperviseHomeBinding
    lateinit var navController: NavController
    lateinit var sharedPref: SharedPref
    val EXTRA_RESULT_CODE = "resultCode"
    val EXTRA_RESULT_INTENT = "resultIntent"
    private val REQUEST_SCREENSHOT = 59706
    private var mgr: MediaProjectionManager? = null
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
        }
        mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

        startActivityForResult(
            mgr!!.createScreenCaptureIntent(),
            REQUEST_SCREENSHOT
        )
        checkOverlayPermission();

    }

    override fun onResume() {
        startService()
        super.onResume()
    }
    private fun checkOverlayPermission() {
        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

    // method for starting the service
    fun startService() {
        if (Settings.canDrawOverlays(this)) {
            // start the service based on the android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, ForegroundService::class.java))
            } else {
                startService(Intent(this, ForegroundService::class.java))
            }
        }else{
            val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivity(myIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SCREENSHOT) {
            if (resultCode == RESULT_OK) {
                val i = Intent(this, ScreenshotServiceOld::class.java)
                    .putExtra(EXTRA_RESULT_CODE, resultCode)
                    .putExtra(EXTRA_RESULT_INTENT, data)
                startService(i)
            }
        }
   //     finish()
    }

}