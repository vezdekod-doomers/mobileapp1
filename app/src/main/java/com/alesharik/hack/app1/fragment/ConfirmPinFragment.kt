package com.alesharik.hack.app1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alesharik.hack.app1.R
import com.alesharik.hack.app1.data.PinManager
import com.alesharik.hack.app1.getPinManager
import com.alesharik.hack.app1.util.PinChecker
import kotlinx.android.synthetic.main.fragment_confirm_pin.view.*

class ConfirmPinFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val pin = requireArguments().getString("pin")!!
        val view = inflater.inflate(R.layout.fragment_confirm_pin, container, false)
        view.pin.maxEms = PinManager.PIN_LENGTH
        view.set_pin_btn.isEnabled = false
        view.set_pin_btn.setOnClickListener {
            getPinManager().setPin(pin)
            findNavController().navigate(R.id.biometryFragment)
        }
        view.pin.addTextChangedListener(PinChecker(requireContext(), onError = {
            if (it == null) {
                view.error_frame.visibility = View.GONE
                view.set_pin_btn.isEnabled = true
            } else {
                view.error_frame.visibility = View.VISIBLE
                view.set_pin_btn.isEnabled = false
                view.error.text = it
            }
        }, pin))
        return view
    }
}
