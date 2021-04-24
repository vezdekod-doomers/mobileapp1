package com.alesharik.hack.app1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alesharik.hack.app1.R
import com.alesharik.hack.app1.data.PinManager
import com.alesharik.hack.app1.util.PinChecker
import kotlinx.android.synthetic.main.fragment_home.view.set_pin_btn
import kotlinx.android.synthetic.main.fragment_login.view.pin
import kotlinx.android.synthetic.main.fragment_set_pin.view.*

class SetPinFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_pin, container, false)
        view.pin.maxEms = PinManager.PIN_LENGTH
        view.set_pin_btn.isEnabled = false
        view.set_pin_btn.setOnClickListener {
            val pin = view.pin.text.toString()
            findNavController().navigate(R.id.confirmPinFragment, Bundle().apply {
                putString("pin", pin)
            })
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
        }))
        return view
    }
}
