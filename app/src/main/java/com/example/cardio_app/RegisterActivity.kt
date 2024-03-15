package com.example.cardio_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardio_app.data.model.LoginResponse
import com.example.cardio_app.data.model.RegisterRequest
import com.example.cardio_app.data.restapi.RegisterApi
import com.google.android.material.textfield.TextInputLayout
import io.ktor.http.HttpStatusCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener{
            //получение данных из полей с экрана
            val firstNameText = findViewById<EditText>(R.id.editTextF)
            val first = firstNameText.text.toString()

            val nameNameText = findViewById<EditText>(R.id.editTextI)
            val name = nameNameText.text.toString()

            val lastNameText = findViewById<EditText>(R.id.editTextO)
            val last = lastNameText.text.toString()

            val fio: String = first + " " + name + " " + last;
            var sex:String = ""
            val sex_m = findViewById<RadioButton>(R.id.man)
            val sex_w = findViewById<RadioButton>(R.id.man)
            if (sex_m.isChecked) sex = "M"
            if (sex_w.isChecked) sex = "Ж"

            val loginText = findViewById<EditText>(R.id.editTextLogin)
            val passwordText = findViewById<EditText>(R.id.editTextPass)
            val login = loginText.text.toString()
            val password = passwordText.text.toString()
            val passwordText2 = findViewById<EditText>(R.id.editTextPass2)
            val password2 = passwordText2.text.toString()


            if (passwordText.text.toString() != passwordText2.text.toString()) {
                passwordText2.setSelectAllOnFocus(true)
                Toast.makeText(this@RegisterActivity, "Введеные пароли не совпадают", Toast.LENGTH_SHORT).show()
            }
            else {


                //Retrofit для соединения с сервером
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.1.13:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(RegisterApi::class.java)

                val requestBody = RegisterRequest(login, password, fio, sex)

                val call = apiService.postregister(requestBody)




                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            startActivity(
                                Intent(
                                    this@RegisterActivity, SurveyActivity::class.java
                                )
                            ) //переход на активити после авторизации
                        } else {
                            Toast.makeText(this@RegisterActivity, "Пользователь с таким логином уже существует.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Проверьте Интернет-соединение.",
                            Toast.LENGTH_SHORT
                        ).show()
                        t.printStackTrace()
                    }
                })
            }
        }
    }


}