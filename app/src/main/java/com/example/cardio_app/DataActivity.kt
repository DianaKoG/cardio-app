package com.example.cardio_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity


class DataActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        supportActionBar?.title = "Журнал статистики"

        // находим список
        val Data = findViewById<View>(R.id.datalist) as ListView
        val item = listOf<String>()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, item
        )

        Data.adapter = adapter

        //экран с показателями при нажатии на элемент списка
        Data.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val intent = Intent(this@DataActivity, SurveyActivity::class.java)
            intent.putExtra("selectedItem", selectedItem)
            startActivity(intent)
        }

        val buttonAdd = findViewById<ImageButton>(R.id.buttonAdd)
        buttonAdd.setOnClickListener{
            startActivity(
                Intent(
                    this@DataActivity, StatisticActivity::class.java
                )
            )
        }
    }
}