package com.example.cardio_app

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.cardio_app.data.model.SurveyRequest
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


class SurveyActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)


        val noneCount = handleCount(check(intent))
        if (noneCount > 0){
            startActivity(
                Intent(
                    this@SurveyActivity, DataActivity::class.java
                )
            )
        }


        val button = findViewById<Button>(R.id.buttonSurvey)
        button.setOnClickListener {
            //получение данных из полей с экрана
            val dateText = findViewById<EditText>(R.id.editTextA).toString()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
            val date: LocalDate = try {
                LocalDate.parse("21.03.1980", formatter)
            } catch (e: DateTimeParseException) {
                LocalDate.of(2000, 3, 21)
            }
            val weightText = findViewById<EditText>(R.id.editTextW).text.toString()
            val format = DecimalFormat("#.#")
            val weight = format.parse(weightText)?.toFloat()
            val spinner = findViewById<View>(R.id.spinnerfamily) as Spinner
            val selected = spinner.selectedItem.toString()
            var bool: Boolean = false
            if (selected == "Да") bool = true
            else {
                if (selected == "Нет") bool = false
                else {
                    if (selected == "Не знаю") bool = false
                }
            }

            val checks: ArrayList<CheckBox> = arrayListOf()
            val ids = arrayListOf(
                R.id.checkBox0,
                R.id.checkBox1,
                R.id.checkBox2,
                R.id.checkBox3,
                R.id.checkBox4
            )
            var countt = 0
            var flag = 0
            for (i in 0 until 5) {
                checks.add(findViewById(ids[i]))
            }

            for (i in 0 until 5) {
                if (checks[i].isChecked) {
                    flag += 1
                    val requestBody = SurveyRequest(date.toString(), weight, bool, i)
                    countt += callAdd(requestBody, intent)
                }
            }

            if (countt == flag) {
                startActivity(
                    Intent(
                        this@SurveyActivity, DataActivity::class.java
                    )
                )
            }
            else {
                Toast.makeText(
                    this@SurveyActivity,
                    "Что-то пошло не так",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }


    }

}

    private fun callAdd(Body: SurveyRequest, intent: Intent): Int {
        val result = CoroutineScope(Dispatchers.IO).async {
            val client = OkHttpClient()
            val token = intent.getStringExtra("token") ?: "45d7f5b1-bf3a-4dd1-be60-0d8b8f86ef3d"
            val headers = Headers.Builder()
                .add("Authorization", token)
                .build()
            val requestBody =
                Gson().toJson(Body).toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("http://192.168.1.6:8080/auth/addinfo")
                .headers(headers)
                .post(requestBody)
                .build()
            val call = client.newCall(request)
            val response: Response = call.execute()
            response
        }
        val deferredResponse: Deferred<Response> = result
        val response: Response = runBlocking {
            deferredResponse.await()
        }
        return if (response.isSuccessful) 1
        else 0
    }

    fun check(intent: Intent): Response {
        val result = CoroutineScope(Dispatchers.IO).async {
            val client = OkHttpClient()
            val token = intent.getStringExtra("token") ?: "45d7f5b1-bf3a-4dd1-be60-0d8b8f86ef3d"
            val headers = Headers.Builder()
                .add("Authorization", token)
                .build()
            val request = Request.Builder()
                .url("http://192.168.1.6:8080/auth/findinfo")
                .headers(headers)
                .build()
            val call = client.newCall(request)
            val response: Response = call.execute()
            response
        }
        val deferredResponse: Deferred<Response> = result
        val response: Response = runBlocking {
            deferredResponse.await()
        }
        return response
    }

    fun handleCount(response: Response): Int {
        val jsonString = response.body?.string()
        val gson = Gson()
        val jsonArray = gson.fromJson(jsonString, Array<Any>::class.java)
        val count = jsonArray.size
        return count
    }




