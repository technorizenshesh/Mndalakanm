package com.techno.mndalakanm.Ui.Home

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.ActivitySuperviseHomeBinding
import com.vilborgtower.user.utils.Constant
import com.vilborgtower.user.utils.PrefManager
import java.util.*

class SuperviseHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivitySuperviseHomeBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_supervise_home)
        val host: NavHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_home_fragment_container)
                as NavHostFragment? ?: return
        navController = host.navController
        setupWithNavController(binding.navView, navController)
        binding. imgHeader .setOnClickListener {
           onBackPressed()
        }
    }


}