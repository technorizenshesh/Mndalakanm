package com.techno.mndalakanm.Ui.setupParent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techno.mndalakanm.R
import com.techno.mndalakanm.Ui.Home.SuperviseHomeActivity
import com.techno.mndalakanm.databinding.FragmentNodeviceParentBinding

class NodeviceParentFragment : Fragment() {
    lateinit var binding: FragmentNodeviceParentBinding
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_nodevice_parent, container, false)
        if (container != null) {
            navController = container.findNavController()
        }
        binding.header.imgMenu.visibility= View.VISIBLE
        binding.header.imgHeader.setImageResource(R.drawable.setting)
        binding.header.imgHeader.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "parent")
            Log.e("TAG", "onCreateView: "+"--------------------" )
            navController.navigate(R.id.action_splash_to_menu_fragment, bundle)

        }
        binding.header.imgMenu.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "parent")
            navController.navigate(R.id.action_splash_to_support_fragment, bundle)

        }
        binding. btnSignIn.setOnClickListener{
            val intent = Intent( activity, SuperviseHomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return binding.root
    }

}