package com.alesharik.hack.app1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.alesharik.hack.app1.data.PinManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pinManager = getPinManager()

        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller = navFragment.navController
        if (pinManager.containsPin()) {
            controller.navigate(R.id.loginFragment)
        } else {
            controller.navigate(R.id.welcomeFragment)
        }
    }
}
