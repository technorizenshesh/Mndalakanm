package com.techno.mndalakanm.Ui.LoginSignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentLoginTypeBinding


class LoginTypeFragment : Fragment() {
    private lateinit var binding : FragmentLoginTypeBinding
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater
            ,R.layout.fragment_login_type,
            container, false)
        if (container != null) {
            navController = container.findNavController()
        }
        binding.childCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "child")
            navController.navigate(R.id.action_splash_to_login_by_child,bundle)

        }
        binding.parentCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "parent")
            navController.navigate(R.id.action_splash_to_login_by,bundle)

        }

        return binding.root
    }

}