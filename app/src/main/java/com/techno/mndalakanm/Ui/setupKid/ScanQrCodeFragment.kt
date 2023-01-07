package com.techno.mndalakanm.Ui.setupKid

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentChangePinBinding
import com.techno.mndalakanm.databinding.FragmentEnterPairingCodeBinding
import com.techno.mndalakanm.databinding.FragmentScanQrBinding

class ScanQrCodeFragment : Fragment() {
    lateinit var binding: FragmentScanQrBinding
    private val editTexts: Array<EditText>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_scan_qr, container, false)
        binding.header. imgHeader .setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnSignIn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "child")
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_child_details_fragment,bundle)
        }

        return binding.root
    }
}
