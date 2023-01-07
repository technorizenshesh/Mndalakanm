package com.techno.mndalakanm.Ui.setupParent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentOtherPairingOptionBinding
import com.techno.mndalakanm.databinding.FragmentShowQRBinding

class ShowQRFragment : Fragment() {

    private lateinit var binding: FragmentShowQRBinding
    lateinit var navController: NavController
    var type = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_show_q_r,
            container, false
        )
        if (container != null) {
            navController = container.findNavController()
        }
        if (getArguments() != null) {
            type = arguments?.getString("type").toString()
        }
        binding.header.imgHeader.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.otherPairingCodes.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "parent")
            navController.navigate(R.id.action_splash_to_NodeviceParentFragment, bundle)
        }
        binding.scann.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "parent")
            navController.navigate(R.id.action_splash_to_NodeviceParentFragment, bundle)
        }

        return binding.root
    }
}