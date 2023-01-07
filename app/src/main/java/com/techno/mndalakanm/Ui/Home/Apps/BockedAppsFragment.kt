package com.techno.mndalakanm.Ui.Home.Apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.techno.mndalakanm.R
import com.techno.mndalakanm.Ui.Home.HomeFragment
import com.techno.mndalakanm.databinding.FragmentBockedAppsBinding
import com.techno.mndalakanm.databinding.FragmentHomeBinding
import com.vilborgtower.user.utils.Constant
import com.vilborgtower.user.utils.PrefManager

class BockedAppsFragment : Fragment() {
    lateinit var binding:  FragmentBockedAppsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_bocked_apps, container, false)

        return binding.root
    }
}