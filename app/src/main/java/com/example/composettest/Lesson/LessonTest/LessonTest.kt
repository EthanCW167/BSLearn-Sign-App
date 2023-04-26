package com.example.composettest.Lesson.LessonTest

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Lesson.LessonPreviewViewModel

@Composable
fun LessonTest(
    navController: NavController,
    viewModel: LessonTestViewModel = hiltViewModel(),
    questionId: Int,
    orderNum: Int
){
    println(viewModel.getLessonId)
    println(viewModel.getOrderNum)
    println(viewModel.questionType)

}