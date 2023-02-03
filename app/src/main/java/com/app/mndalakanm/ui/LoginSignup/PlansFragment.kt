package com.app.mndalakanm.ui.LoginSignup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import  com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentPlansBinding

class PlansFragment : Fragment() {
    private lateinit var binding: FragmentPlansBinding
    lateinit var navController: NavController
    var type = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_plans,
            container, false
        )
        if (container != null) {
            navController = container.findNavController()
        }
        if (getArguments() != null) {
            type = arguments?.getString("type").toString()
        }
        binding.header.imgHeader.setOnClickListener{
            activity?.onBackPressed()
        }
        binding.selectedCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "parent")
            navController.navigate(R.id.action_splash_to_phone,bundle)
        }

        return binding.root
    }

}