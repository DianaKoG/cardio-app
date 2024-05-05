package com.example.cardio_app.data.restapi

import com.example.cardio_app.data.model.LoginResponse
import com.example.cardio_app.data.model.RegisterRequest
import com.example.cardio_app.data.model.SurveyRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SurveyApi {

    @GET("auth/findinfo")
    fun getinfo(
        @Header("Authorization") token: String
    )
    @POST("auth/addinfo")
    fun postinfo(
        @Header("Authorization") token: String,
        @Body requestBody: SurveyRequest
    )
}