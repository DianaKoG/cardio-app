package com.example.cardio_app.data.restapi

import com.example.cardio_app.data.model.LoginResponse
import com.example.cardio_app.data.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//API-интерфейс для регистрации
interface RegisterApi {
    @POST("register")
    fun postregister (@Body requestBody: RegisterRequest
    ): Call<LoginResponse>
}