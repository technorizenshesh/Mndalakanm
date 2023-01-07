package com.techno.mndalakanm.Ui.setupParent.Menu.Setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentAboutBinding
import com.techno.mndalakanm.databinding.FragmentAcInfoBinding


class AcInfoFragment : Fragment() {
    lateinit var binding: FragmentAcInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ac_info, container, false)
        binding.header.imgHeader.setOnClickListener {
            activity?.onBackPressed()
        }
        /*  binding.subscription.setOnClickListener {

          }*/
        return binding.root
    }
}