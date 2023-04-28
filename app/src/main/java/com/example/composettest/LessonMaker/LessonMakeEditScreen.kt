package com.example.composettest.LessonMaker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FQuestion
import com.example.composettest.Domain.model.Question
import com.example.composettest.Screen

@Composable
fun LessonMakerEditScreen(
    navController: NavController,
    viewModel: LessonMakerEditViewModel = hiltViewModel(),
    lessonId: String
) {

    val state = viewModel.lessonEditState.value

    val lessonTitle = viewModel.lessonName.value



    //if (viewModel.lessonEdit == null) {
    //    var lessonEdit = state.lessons[lessonIndex]
    //} else {
    //    var lessonEdit = viewModel.lessonEdit
    //}

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        topBar(navController)
        previewLessonButton()
        TitleTextField(
            value = lessonTitle.name,
            viewModel = viewModel)

        Divider(color = Color.White, modifier = Modifier
            .padding(top = 2.dp)
            .padding(horizontal = 20.dp)
            .padding(bottom = 5.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
        SignQuestionBox(state = state)
        QuestionsList(navController = navController, state = state)
    }
}

@Composable
fun previewLessonButton(){
    Box(modifier = Modifier
        .width(300.dp)
        .height(60.dp)
        .padding(5.dp)
        .background(Color(114, 112, 248), shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center){
        Text(text = "Preview Lesson", color = Color.White, fontSize = 24.sp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Play Arrow")
        }

    }
}

@Composable
fun TitleTextField(
     value: String,
     viewModel: LessonMakerEditViewModel
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalArrangement = Arrangement.Start){
        BasicTextField(
            value = value,
            textStyle = TextStyle.Default.copy(fontSize = 22.sp, color = Color.White),
            onValueChange = { viewModel.onEvent(EditLessonEvent.EnteredTitle(it)) })
    }
}

@Composable
fun SignQuestionBox(state: LessonMakerSelectedState){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(23.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        .height(80.dp)
        .background(Color.White, shape = RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
            Row(modifier = Modifier.padding(0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Row() {
                    Icon(imageVector = Icons.Default.QuestionMark, contentDescription = "Question")
                    Text(text = "${state.lesson.questions} Questions", modifier = Modifier.padding(horizontal = 5.dp))
                }
                Spacer(modifier = Modifier.width(30.dp))
                Row() {
                    Icon(imageVector = Icons.Default.WavingHand, contentDescription = "Signs")
                    Text(text = "+${state.lesson.signs} Signs to learn", modifier = Modifier.padding(horizontal = 5.dp))
                }


        }

    }
}

@Composable
fun QuestionsList(navController: NavController, state: LessonMakerSelectedState){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .padding(top = 12.dp)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(Color.White, shape = RoundedCornerShape(20.dp))){

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()){

            items(state.lesson.questionsList) { question ->
                QuestionCard(navController = navController, question = question, orderNum = 1)
                Divider(color = Color.Black, modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun QuestionCard(navController: NavController, question: FQuestion, orderNum: Int){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = "$orderNum", fontSize = 60.sp, modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = "${question.questionType}", fontSize = 20.sp)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
             horizontalArrangement = Arrangement.End) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(40.dp))
        }
    }
}