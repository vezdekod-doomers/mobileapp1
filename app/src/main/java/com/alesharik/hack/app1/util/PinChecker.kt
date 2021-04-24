package com.alesharik.hack.app1.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import com.alesharik.hack.app1.R
import com.alesharik.hack.app1.data.PinManager

class PinChecker(private val context: Context, private val onError: (error: String?) -> Unit, private val referencePin: String? = null): TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        val pin = s.toString()
        if (pin.length < PinManager.PIN_LENGTH) {
            val msg = context.getString(R.string.error_pin_short).replace("{}", PinManager.PIN_LENGTH.toString())
            onError(msg)
            return
        }
        if (referencePin != null && referencePin != pin) {
            val msg = context.getString(R.string.error_wrong_pin)
            onError(msg)
            return
        }
        onError(null)
    }
}
