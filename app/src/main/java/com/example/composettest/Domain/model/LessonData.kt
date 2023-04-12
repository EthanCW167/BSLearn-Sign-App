package com.example.composettest.Domain.model

sealed class LessonData{
    data class Question(
        val questionType: String,
        val signData: signData,
        val isCorrect: Boolean
    )
    data class LearnSign(
        val signData: signData,
    )
/*
    override fun toString(): String = Uri.encode(Gson().toJson(this))

 */
}
