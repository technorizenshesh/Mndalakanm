package com.techno.mndalakanm.Ui.setupKid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.techno.mndalakanm.R
import com.techno.mndalakanm.Ui.Home.SuperviseHomeActivity
import com.techno.mndalakanm.databinding.FragmentChildPermissionBinding
import com.techno.mndalakanm.databinding.FragmentRequestAllPermissionsBinding

class RequestAllPermissionsFragment : Fragment() {
    lateinit var  binding: FragmentRequestAllPermissionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater
            ,R.layout.fragment_request_all_permissions, container, false)

        binding.header. imgHeader .setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnSignIn.setOnClickListener {
           /* val bundle = Bundle()
            bundle.putString("type", "child")
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_splash_to_request_all_permissions_fragment, bundle)

*/

            val intent = Intent( activity, SuperviseHomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return  binding.root

    }
}