package com.example.cardio_app.data.model

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("token")
    val usertoken: String = String(),
)