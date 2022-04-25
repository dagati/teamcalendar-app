package com.example.dagatiproject_app.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginResponse(
    @SerializedName("token") var token: String
)
