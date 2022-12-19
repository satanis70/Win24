package com.example.win24.model

import androidx.annotation.Keep

@Keep
data class Quizsport(
    val answer1: Answer1,
    val answer2: Answer1,
    val answer3: Answer1,
    val question: String
)