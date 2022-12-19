package com.example.win24

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.edit
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.win24.model.QuizModel
import com.example.win24.retrofit.Api
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity() {
    val arrayListQuestions = ArrayList<QuizModel>()
    var position = 0
    var correctAnswer = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setBackgroundImage()
        getQuestionList(position)
        button_next.setOnClickListener {
            position+=1
            if (arrayListQuestions[0].quizsport.size==position){
                val sharedPrefCapital = this.getSharedPreferences("score", Context.MODE_PRIVATE)
                sharedPrefCapital.edit {
                    putString("score", correctAnswer.toString())
                    putString("size", arrayListQuestions[0].quizsport.size.toString())
                }
                startActivity(Intent(this, ResultActivity::class.java))
                finish()
            } else {
                getQuestionList(position)
            }
        }
    }

    private fun setBackgroundImage(){
        Glide.with(this).load("http://49.12.202.175/win24/gradient.png")
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ){
                    game_constraint.background = resource
                }
            })
    }

    private fun getQuestionList(positionCurrent: Int) {
        arrayListQuestions.clear()
        button_next.isEnabled = false
        CoroutineScope(Dispatchers.IO).launch{
            val api = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/win24/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
            val response = api.getQuestions().awaitResponse()
            if (response.isSuccessful){
                arrayListQuestions.add(response.body()!!)
                Log.i("ARRAY", arrayListQuestions.toString())
            }
            launch(Dispatchers.Main){
                    textView_question.text = arrayListQuestions[0].quizsport[positionCurrent].question
                    button_answer1.text = arrayListQuestions[0].quizsport[positionCurrent].answer1.name
                    button_answer2.text = arrayListQuestions[0].quizsport[positionCurrent].answer2.name
                    button_answer3.text = arrayListQuestions[0].quizsport[positionCurrent].answer3.name
                    button_answer1.setBackgroundResource(R.drawable.round_button)
                    button_answer2.setBackgroundResource(R.drawable.round_button)
                    button_answer3.setBackgroundResource(R.drawable.round_button)
                    button_answer1.isEnabled = true
                    button_answer2.isEnabled = true
                    button_answer3.isEnabled = true
                    button_answer1.setOnClickListener {
                        if (arrayListQuestions[0].quizsport[positionCurrent].answer1.trueorfalse=="true"){
                            button_answer1.setBackgroundResource(R.drawable.round_button_true)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                            correctAnswer+=1
                        } else {
                            button_answer1.setBackgroundResource(R.drawable.round_button_false)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                        }
                    }
                    button_answer2.setOnClickListener {
                        if (arrayListQuestions[0].quizsport[positionCurrent].answer2.trueorfalse=="true"){
                            button_answer2.setBackgroundResource(R.drawable.round_button_true)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                            correctAnswer+=1
                        } else {
                            button_answer2.setBackgroundResource(R.drawable.round_button_false)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                        }
                    }
                    button_answer3.setOnClickListener {
                        if (arrayListQuestions[0].quizsport[positionCurrent].answer3.trueorfalse=="true"){
                            button_answer3.setBackgroundResource(R.drawable.round_button_true)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                            correctAnswer+=1
                        } else {
                            button_answer3.setBackgroundResource(R.drawable.round_button_false)
                            button_answer1.isEnabled = false
                            button_answer2.isEnabled = false
                            button_answer3.isEnabled = false
                            button_next.isEnabled = true
                        }
                    }
            }
        }
    }
}