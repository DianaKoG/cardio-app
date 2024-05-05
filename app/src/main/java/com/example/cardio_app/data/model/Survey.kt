package com.example.cardio_app.data.model

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

data class Survey(

    @SerializedName("age")
    var userage: Date? = Date(),

    @SerializedName("weight")
    var userweight: String? = String(),

    @SerializedName("heritage")
    var userheritage: String? = String(),

    @SerializedName("habit")
    var userhabit: String? = String(),
)
data class SurveyRequest(val age: String, val weight: Float?, val heritage: Boolean, val habits: Int)