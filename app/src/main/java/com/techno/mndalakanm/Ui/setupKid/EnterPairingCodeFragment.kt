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

class EnterPairingCodeFragment : Fragment() {
    lateinit var binding: FragmentEnterPairingCodeBinding
    private val editTexts: Array<EditText>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_enter_pairing_code, container, false)
        binding.header. imgHeader .setOnClickListener {
            activity?.onBackPressed()
        }
        configOtpEditText(binding.et1, binding.et2, binding.et3, binding.et4, binding.et5, binding.et6)
        binding.btnSignIn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "child")
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_child_details_fragment,bundle)
        }
        binding.btnScanQrIn.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("type", "child")
            Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_scan_qr_fragment,bundle)

        }

        return binding.root
    }
    fun configOtpEditText(vararg etList: EditText) {
        val afterTextChanged = { index: Int, e: Editable? ->
            val view = etList[index]
            val text = e.toString()
            when (view.id) {
                etList[0].id -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                }
                etList[etList.size - 1].id -> {
                    if (text.isEmpty()) etList[index - 1].requestFocus()
                }
                else -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                    else etList[index - 1].requestFocus()
                }
            }
            false
        }
        etList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged { afterTextChanged(index, it) }
        }
    }
}
