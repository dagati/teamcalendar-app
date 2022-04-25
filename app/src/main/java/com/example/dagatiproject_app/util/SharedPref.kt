package com.example.dagatiproject_app.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SharedPref {


    const val emailKey = "Email"
    const val pwKey = "PW"
    const val tokenKey = "Token"


    var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        sharedPreferences = EncryptedSharedPreferences(
            context,
            "secret.txt",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    fun putString(key: String, data: String) {
        sharedPreferences!!.edit().run {
            putString(key, data)
            apply()
        }
    }

    fun getString(key: String): String? = sharedPreferences?.getString(key, null)

}