package com.techno.mndalakanm.Ui.setupParent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {


    lateinit var binding: FragmentMenuBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.header.imgHeader.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.subscription.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_subscr_fragment)

        }
        binding.support.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_support_fragment)

        }
        binding.setting.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_setting_fragment)

        }
        binding.about.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_about_fragment)

        }
        return binding.root
    }

}