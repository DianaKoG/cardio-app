package com.example.cardio_app.data.model

import com.google.gson.annotations.SerializedName

data class Survey(
    @SerializedName("token")
    val usertoken: String = String(),
)
