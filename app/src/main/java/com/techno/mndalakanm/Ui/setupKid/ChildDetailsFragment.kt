package com.techno.mndalakanm.Ui.setupKid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentChildDetailsBinding


class ChildDetailsFragment : Fragment() {

    lateinit var binding: FragmentChildDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_child_details, container, false
        )
        binding.header. imgHeader .setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnSignIn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("type", "child")
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_splash_to_child_permission_fragment, bundle)


        }

        return binding.root
    }

}