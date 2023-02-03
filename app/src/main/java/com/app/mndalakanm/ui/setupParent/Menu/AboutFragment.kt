package com.app.mndalakanm.ui.setupParent.Menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import  com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        binding.header.imgHeader.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.terms.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_splash_to_terms_fragment)

        }
        binding.privacy.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_splash_to_privacy_fragment)

        }
        return binding.root
    }
}