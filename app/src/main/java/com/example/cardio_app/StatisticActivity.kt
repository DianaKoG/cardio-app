package com.example.cardio_app


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cardio_app.data.dataclass.Statistic
import com.example.cardio_app.data.dataclass.StatisticAdapter
import com.example.cardio_app.data.model.StatisticRequest
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


class StatisticActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        val data = arrayListOf<Statistic>(
            Statistic("Верхнее АД\nмм.рт.ст.", 120, 0.0),
            Statistic("Нижнее АД\nмм.рт.ст.", 80,0.0),
            Statistic("Холестерин\nммоль/л", 0, 5.5),
            Statistic("Глюкоза\nммоль/л", 0, 4.0),
            Statistic("Активность\nмин", 30, 0.0)
        )


        val dataList: ListView = findViewById(R.id.statisticList)
        val adapter = StatisticAdapter(this, R.layout.statistic_list, data)
        dataList.adapter = adapter

        val button=findViewById<Button>(R.id.buttonStatistic)

        button.setOnClickListener{

            val data_edit = arrayListOf<String>()
            for (i in 0 until 5){

                val viewHolder = dataList.adapter.getView(i, findViewById<EditText>(R.id.countView), dataList)
                val v = com.example.cardio_app.data.dataclass.ViewHolder(viewHolder)
                val editText = v.countView

                data_edit.add(v.countView.text.toString())
            }
            val pressure_high = data_edit[0].toInt()
            val pressure_low = data_edit[1].toInt()
            val cholesterol = data_edit[2].toFloat()
            val gluc = data_edit[3].toFloat()
            val active = data_edit[4].toInt()
            val body = StatisticRequest(pressure_high, pressure_low, cholesterol, gluc, active)
            val result = CoroutineScope(Dispatchers.IO).async {
                val client = OkHttpClient()
                val token = "45d7f5b1-bf3a-4dd1-be60-0d8b8f86ef3d"
                val headers = Headers.Builder()
                    .add("Authorization", token)
                    .build()
                val requestBody =
                    Gson().toJson(body).toRequestBody("application/json".toMediaType())
                val request = Request.Builder()
                    .url("http://192.168.1.6:8080/auth/addstatistic")
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
            if (response.isSuccessful) {
                startActivity(
                    Intent(
                        this@StatisticActivity, DataActivity::class.java
                    )
                )
            }
            else {
                Toast.makeText(
                    this@StatisticActivity,
                    "Что-то пошло не так",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }
}