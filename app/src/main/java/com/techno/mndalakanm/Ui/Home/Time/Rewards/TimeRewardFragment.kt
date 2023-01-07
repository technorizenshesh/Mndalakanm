package com.techno.mndalakanm.Ui.Home.Time.Rewards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentBockedAppsBinding
import com.techno.mndalakanm.databinding.FragmentTimeRewardBinding

class TimeRewardFragment : Fragment() {


    lateinit var binding: FragmentTimeRewardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_time_reward, container, false)

        return binding.root
    }}