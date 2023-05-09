package com.example.composettest.Lesson

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.composettest.Screen

@Composable
fun PopScreen(navController: NavController){
    navController.navigate(route = Screen.LessonPreviewScreen.route + "?id={id}"){
        popUpTo(route = Screen.LessonSummaryScreen.route + "?lessonId={lessonId}&lessonTitle={lessonTitle}"){
            inclusive = true
        }
        popUpTo(route = Screen.HomeScreen.route){
            inclusive = false
        }
    }
}