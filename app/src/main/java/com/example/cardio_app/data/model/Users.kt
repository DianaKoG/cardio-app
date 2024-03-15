package com.example.cardio_app.data.model

import android.widget.EditText
import com.google.gson.annotations.SerializedName

data class Users (
    @SerializedName("login")
    val userlogin: String = String(),

    @SerializedName("password")
    val userpassword: String = String(),

    @SerializedName("fio")
    var userfio: String? = String(),

    @SerializedName("sex")
    var usersex: String? = String(),
)

data class LoginRequest(val login: String, val password: String)

data class RegisterRequest(val login: String, val password: String, val fio:String, val sex: String)

data class LoginResponse(val token: String)