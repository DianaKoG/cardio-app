package com.example.cardio_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.cardio_app.data.model.LoginRequest
import com.example.cardio_app.data.model.LoginResponse
import com.example.cardio_app.data.model.SurveyRequest
import com.example.cardio_app.data.restapi.UserApi
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //гипер-текст для регистрации
        val linkTextView = findViewById<TextView>(R.id.linkTextView)
        val text = "Зарегистрироваться"

        val spannableString = SpannableString(text)
        spannableString.setSpan(UnderlineSpan(), 0, text.length, 0)

        linkTextView.text = spannableString
        linkTextView.movementMethod = LinkMovementMethod.getInstance()
        //переход на экран регистрации
        linkTextView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }


        val button = findViewById<ImageButton>(R.id.button)
        button.setOnClickListener{
            //получение данных из полей с экрана
            val loginText = findViewById<EditText>(R.id.editTextText)
            val passwordText = findViewById<EditText>(R.id.editTextTextPassword)
            val login = loginText.text.toString()
            val password = passwordText.text.toString()

            //Retrofit для соединения с сервером
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.6:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(UserApi::class.java)

            val requestBody = LoginRequest(login, password)

            val call = apiService.postlogin(requestBody)




            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val token = response.body().toString()
                        val intent = Intent(this@LoginActivity, SurveyActivity::class.java)
                        intent.putExtra("token", "45d7f5b1-bf3a-4dd1-be60-0d8b8f86ef3d")
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this@LoginActivity, "API Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    //
                    Toast.makeText(this@LoginActivity, "Connection error", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        }


    }

}