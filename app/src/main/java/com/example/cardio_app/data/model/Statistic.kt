package com.example.cardio_app.data.model

data class StatisticRequest(val pressure_high: Int,
                            val pressure_low: Int,
                            val cholesterol: Float?,
                            val gluc: Float?,
                            val active: Int)