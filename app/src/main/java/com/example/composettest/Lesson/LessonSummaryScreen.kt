package com.example.composettest.Lesson

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
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
import com.example.composettest.Domain.model.Question

@Composable
fun LessonSummaryScreen(
    navController: NavController,
    viewModel: LessonSummaryViewModel = hiltViewModel(),
    lessonId: Int
){

/*
    val questionsUi: List<Question> by viewModel.question.collectAsState(initial = emptyList())
    println(questionsUi.size)
    println(questionsUi.size)
    println(questionsUi.size)
    println(questionsUi.size)
    val questionTest = questionsUi


 */

    val state = viewModel.qState.value

    println(state.questions.size)
    println(state.questions.size)
    println(state.questions.size)
    println(state.questions.size)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Lesson Sign View",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            lessonNumBox(lessonNum = 1)
        }
        Divider(
            color = Color.Black, modifier = Modifier
                .padding(16.dp)
                .height(1.dp)
                .fillMaxWidth()
        )


        Column(
            Modifier
                .padding(top = 10.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Lesson Complete",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(top = 5.dp)
                    .padding(horizontal = 5.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.questions) { question ->
                    QuestionSummaryCard(question = question)
                }
            }

            Divider(
                color = Color.Black, modifier = Modifier
                    .padding(20.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { },
                    modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(72, 69, 221))) {
                    Text(text = "Finish", color = Color.White, fontSize = 20.sp)

                }

            }
        }
        }
    }

@Composable
fun QuestionSummaryCard(question: Question){
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically) {
        Image(imageVector = Icons.Default.Image, contentDescription = "Question Preview", modifier = Modifier
            .height(140.dp)
            .width(180.dp)
            .padding(5.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(20.dp))
            .border(border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(20.dp)))
        Column(modifier = Modifier.fillMaxHeight().padding(bottom = 15.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {
            Row(
                Modifier
                    .width(160.dp)
                    .height(70.dp)
                    .background(Color(106, 192, 115), shape = RoundedCornerShape(20.dp)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Q1", fontSize = 20.sp)
            }

            Icon(imageVector = Icons.Default.Info, contentDescription = "View summary", Modifier.size(25.dp))
        }
        Icon(imageVector = Icons.Default.Check, contentDescription = "Check Mark", Modifier.size(25.dp))
    }
}