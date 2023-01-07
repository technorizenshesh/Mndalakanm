package com.techno.mndalakanm.Ui.LoginSignup

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentLoginByBinding
import com.techno.mndalakanm.databinding.FragmentLoginNoBinding


class LoginNoFragment : Fragment() {

    private lateinit var binding: FragmentLoginNoBinding
    lateinit var navController: NavController
    var type = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login_no,
            container, false
        )
        if (container != null) {
            navController = container.findNavController()
        }
        if (getArguments() != null) {
            type = arguments?.getString("type").toString()
        }



        binding.btnSignIn.setOnClickListener {
           if (type.toString() == "parent"){
               val bundle = Bundle()
               bundle.putString("type", type)
               navController.navigate(R.id.action_splash_to_plans,bundle)

           }else{

           }


        }
        /* binding.parentCard.setOnClickListener {
             val bundle = Bundle()
             bundle.putString("type", "parent")
             navController.navigate(R.id.action_splash_to_login_type)

         }*/

        return binding.root
    }
}

