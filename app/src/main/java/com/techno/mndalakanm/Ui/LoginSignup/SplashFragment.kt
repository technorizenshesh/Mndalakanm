package com.techno.mndalakanm.Ui.LoginSignup

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techno.mndalakanm.R
import com.techno.mndalakanm.Ui.Home.SuperviseHomeActivity
import com.techno.mndalakanm.databinding.FragmentSplashBinding
import com.vilborgtower.user.utils.Utils

class SplashFragment : Fragment() {

    lateinit var utils: Utils
    lateinit var binding: FragmentSplashBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_splash, container,
            false
        )
        if (container != null) {
            navController = container.findNavController()
        }
        //utils = Utils(requireContext())
        // utils?.getFirebaseRegisterId()
        //  checkForPermission()
        handlerMethod()

        return binding.root
    }

    fun checkForPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                1000
            )
        } else {
            // already permission granted
            handlerMethod()
        }
    }

    fun handlerMethod() {
        Handler(Looper.getMainLooper()).postDelayed({
            /* if (PrefManager.getBoolean(Constant.IS_LOGIN)) {
                 *//* val intent = Intent(applicationContext, HomeActivity::class.java)
                 startActivity(intent)
                 finish()*//*
            } else {*/
              navController.navigate(R.id.action_splash_to_login_type)
          /*  val intent = Intent(activity, SuperviseHomeActivity::class.java)
            startActivity(intent)*/
            // activity?.finish()
            //  }
        }, 3000)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) { // If request is cancelled, the result arrays are empty.
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED
            ) {
                handlerMethod()
            } else {
                utils?.showToast(getString(R.string.permission_denied))
            }
        }
    }

}