package com.techno.mndalakanm.Ui.Home.Apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentAppsBinding
import com.techno.mndalakanm.databinding.FragmentGeoBinding

class AppsFragment : Fragment() {
    lateinit var binding: FragmentAppsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_apps,
            container, false
        )
        binding.blockedApps.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.apps_to_block)
        }
        binding.systemApps.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.apps_to_system)
        }
        binding.webFiltering.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.apps_to_web_filtering)
        }

        return binding.root
    }
}