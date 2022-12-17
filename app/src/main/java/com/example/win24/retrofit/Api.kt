package com.example.win24.retrofit

import com.example.win24.model.QuizModel
import retrofit2.Call
import retrofit2.http.GET

interface Api{
    @GET("quizsport.json")
    fun getQuestions(): Call<QuizModel>
}