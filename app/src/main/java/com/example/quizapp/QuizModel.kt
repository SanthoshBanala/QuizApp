package com.example.quizapp

data class QuizModel(
    val id: String,
    val title: String,
    val subtitle:String,
    val time: String,
){
    constructor(): this(id="", title="", subtitle="", time="")
}
