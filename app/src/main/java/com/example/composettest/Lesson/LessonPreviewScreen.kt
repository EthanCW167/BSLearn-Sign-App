package com.example.composettest.Lesson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Screen

@Composable
fun LessonPreviewScreen(
    navController: NavController,
    viewModel: LessonPreviewViewModel = hiltViewModel(),
    id: Int
){

    val title = viewModel.name
    val lessonNum = viewModel.lessonNum
    val signs = viewModel.signs
    val questions = viewModel.questions
    val isCompleted = viewModel.isCompleted

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ) {}

    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(imageVector = Icons.Default.Preview, contentDescription = "Lesson Image Preview", modifier = Modifier
            .height(300.dp)
            .width(350.dp)
            .padding(top = 50.dp)
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White))
        Spacer(modifier = Modifier.size(20.dp))

        if (lessonNum != null) {
            if (title != null) {
                if (signs != null) {
                    if (questions != null) {
                        PreviewDescription(id = id, name = title, lessonNum = lessonNum, signs = signs, questions = questions, description = "Ipsum Lorum", navController = navController)
                    }
                }
            }
        }

    }
}

@Composable
fun PreviewDescription(id: Int, name: String, lessonNum: Int, signs: Int, questions: Int, description: String, navController: NavController){
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White, shape = RoundedCornerShape(20.dp))) {

        title(name, lessonNum)
        Divider(color = Color.Black, modifier = Modifier
            .padding(5.dp)
            .padding(horizontal = 15.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
        PreviewInfoBox(signs, questions)
        Text(text = "Description", modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .fillMaxWidth())
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = description, modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth())
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {navController.navigate(Screen.LessonSignViewScreen.route + "?lessonId=${id}&orderNum=${1}")} ,modifier = Modifier
                .width(200.dp)
                .height(70.dp)
                .padding(15.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(72,69,221)))  {
                Text(text = "Begin Lesson", color = Color.White)

            }}
    }
}

@Composable
fun title(name: String, lessonNum: Int){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

        ) {
        Text(text = name, fontSize = 18.sp, textAlign = TextAlign.Start)
        lessonNumBox(lessonNum)
    }
}

@Composable
fun lessonNumBox(lessonNum: Int){
    Box(
        Modifier
            .size(40.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(
                Color.Black, shape = RoundedCornerShape(10.dp)
            ), contentAlignment = Alignment.Center
    ) {
        Text(text = "$lessonNum", textAlign = TextAlign.Center, color = Color.White, fontSize = 26.sp)
    }
}

@Composable
fun PreviewInfoBox(signs: Int, questions: Int){
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 15.dp)
            .fillMaxWidth()
            .height(70.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
        ) {

        Row(horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.QuestionMark, contentDescription = "Questions")
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "10 Questions")
        }
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.WavingHand, contentDescription = "Signs")
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "+8 Signs to learn")
        }

    }
}
