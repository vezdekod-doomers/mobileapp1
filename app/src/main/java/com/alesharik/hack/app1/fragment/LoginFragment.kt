package com.alesharik.hack.app1.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alesharik.hack.app1.R
import com.alesharik.hack.app1.getPinManager
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {
    private lateinit var biometricManager: BiometricManager
    private val info = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Войти")
        .setSubtitle("Войти через биометрию")
        .setNegativeButtonText("Использовать только пин")
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        .build()
    private lateinit var prompt: BiometricPrompt


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricManager = BiometricManager.from(requireContext())
        prompt = BiometricPrompt(this, ContextCompat.getMainExecutor(requireContext()), object :
            BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                findNavController().navigate(R.id.homeFragment)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                if (errorCode != BiometricPrompt.ERROR_CANCELED) {
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
        val pinManager = getPinManager()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.login_biometry.visibility = if (pinManager.containsBiometry()) {
            View.VISIBLE
        } else {
            View.GONE
        }
        view.login_biometry.setOnClickListener {
            prompt.authenticate(info)
        }
        view.login_btn.setOnClickListener {
            val pin = view.pin.text.toString()
            if (pinManager.isPinRight(pin)) {
                view.error_frame.visibility = View.GONE
                findNavController().navigate(R.id.homeFragment)
            } else {
                view.error_frame.visibility = View.VISIBLE
                view.error.text = requireContext().getString(R.string.error_wrong_pin)
            }
        }
        view.pin.addTextChangedListener(WatcherImpl { view.error_frame.visibility = View.GONE })
        return view
    }

    private inner class WatcherImpl(private val reset: () -> Unit): TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            reset()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }
}
