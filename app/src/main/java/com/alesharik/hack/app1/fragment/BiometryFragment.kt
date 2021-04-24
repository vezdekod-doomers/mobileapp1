package com.alesharik.hack.app1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alesharik.hack.app1.R
import com.alesharik.hack.app1.getPinManager
import kotlinx.android.synthetic.main.fragment_biometry.view.*

class BiometryFragment : Fragment() {
    private lateinit var biometricManager: BiometricManager
    private val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Войти")
        .setSubtitle("Войти через биометрию")
        .setNegativeButtonText("Использовать только пин")
        .setAllowedAuthenticators(BIOMETRIC_WEAK)
        .build()
    private lateinit var prompt: BiometricPrompt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricManager = BiometricManager.from(requireContext())
        prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), object :
            BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    exit(true)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    if (errorCode == BiometricPrompt.ERROR_CANCELED) {
                        exit(false)
                    } else {
                        Toast.makeText(requireContext(), "Ошибка при добавлении биометрии", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onAuthenticationFailed() {
                    Toast.makeText(requireContext(), "Ошибка при добавлении биометрии", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (biometricManager.canAuthenticate(BIOMETRIC_WEAK) != BiometricManager.BIOMETRIC_SUCCESS) {
            exit(false)
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_biometry, container, false)
        view.add_btn.setOnClickListener {
            prompt.authenticate(info)
        }
        view.skip_btn.setOnClickListener { exit(false) }
        return view
    }

    private fun exit(enableBiometry: Boolean) {
        getPinManager().setBiometry(enableBiometry)
        findNavController().navigate(R.id.homeFragment)
    }
}
