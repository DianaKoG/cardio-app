package com.example.cardio_app.data.restapi

import android.widget.EditText
import com.example.cardio_app.data.model.LoginRequest
import com.example.cardio_app.data.model.LoginResponse
import com.example.cardio_app.data.model.Token
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

//API-интерфейс для авторизации
interface UserApi {
    @POST("login")
    fun postlogin (@Body requestBody: LoginRequest
    ): Call<LoginResponse>
}