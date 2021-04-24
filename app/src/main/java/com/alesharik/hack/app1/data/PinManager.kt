package com.alesharik.hack.app1.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.security.Signature
import java.util.*

class PinManager(context: Context) {
    private val prefs: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        prefs = EncryptedSharedPreferences.create(
            "pin",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    fun containsPin(): Boolean = prefs.contains("pin")

    fun setPin(pin: String) = prefs.edit().putString("pin", pin).apply()

    fun isPinRight(pin: String) = prefs.getString("pin", null)!! == pin

    fun containsBiometry(): Boolean = prefs.contains("biometry")

    fun setBiometry(enabled: Boolean) = prefs.edit().putBoolean("biometry", enabled).apply()

    fun checkBiometry(): Boolean = prefs.getBoolean("biometry", false)

    companion object {
        const val PIN_LENGTH = 4
    }
}
