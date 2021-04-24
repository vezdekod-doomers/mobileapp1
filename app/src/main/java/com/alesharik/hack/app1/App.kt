package com.alesharik.hack.app1

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.alesharik.hack.app1.data.PinManager

class App: Application() {
    private lateinit var _pinManager: PinManager
    val pinManager: PinManager
    get() = _pinManager

    override fun onCreate() {
        super.onCreate()
        _pinManager = PinManager(this)
    }
}

fun Fragment.getPinManager() = requireActivity().getPinManager()

fun Activity.getPinManager(): PinManager = (application as App).pinManager
