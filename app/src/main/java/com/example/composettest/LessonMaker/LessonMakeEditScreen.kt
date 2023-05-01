package com.example.composettest.LessonMaker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import com.example.composettest.Screen

@Composable
fun LessonMakerEditScreen(
    navController: NavController,
    viewModel: LessonMakerEditViewModel = hiltViewModel(),
    lessonId: String,
    userId: String
) {

    println(userId)

    val state = viewModel.lessonEditState.value

    val lessonTitle = viewModel.lessonName.value

    println(viewModel.lessonEditState.value.lesson.questionsList.size)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        topBarEdit(navController, userId)
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
        QuestionsList(navController = navController, state = state, lessonId = lessonId, viewModel, userId)
    }
}

@Composable
fun previewLessonButton(){
    Box(modifier = Modifier
        .width(300.dp)
        .height(60.dp)
        .padding(5.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
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
fun QuestionsList(navController: NavController, state: LessonMakerSelectedState, lessonId: String, viewModel: LessonMakerEditViewModel, userId: String){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .padding(top = 12.dp)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(Color.White, shape = RoundedCornerShape(20.dp))){

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)){

            items(state.lesson.questionsList) { question ->
                var index = state.lesson.questionsList.indexOf(question)
                println(index)
                QuestionCard(navController = navController, question = question, orderNum = 1, questionIndex = index, lessonId = lessonId, userId = userId)
                Divider(color = Color.Black, modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                )
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .size(50.dp)
                .shadow(elevation = 5.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color(72, 69, 221))
                .clickable { navController.navigate(Screen.LessonMakerEditQuestionScreen.route + "?lessonId=${lessonId}&questionIndex=${-1}&userId=${userId}") },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "new question",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }
        Row(Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Row(
                modifier = Modifier
                    .width(160.dp)
                    .height(60.dp)
                    .padding(top = 10.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                    .background(Color(72, 69, 221), shape = RoundedCornerShape(20.dp))
                    .clickable {
                        viewModel.onEvent(EditLessonEvent.SaveLesson(lessonId = lessonId))
                        navController.navigateUp()
                               },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "Save Edit", fontSize = 22.sp, color = Color.White)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Default.SaveAlt, contentDescription = "Save Icon" )
            }
        }
    }
}

@Composable
fun QuestionCard(navController: NavController, question: FQuestion, orderNum: Int, questionIndex: Int, lessonId: String, userId: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable { navController.navigate(Screen.LessonMakerEditQuestionScreen.route + "?lessonId=${lessonId}&questionIndex=${questionIndex}&userId=${userId}") },
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

@Composable
fun topBarEdit(navController: NavController, userId: String){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(contentAlignment = Alignment.Center) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.Start) {

                    Button(
                        onClick = { navController.navigate(Screen.LessonMakerOverview.route + "?userId=${userId}") },
                        shape = RoundedCornerShape(60),
                        modifier = Modifier.width(100.dp).shadow(elevation = 5.dp, shape = RoundedCornerShape(60)),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Back" , color = Color.Black, fontSize = 16.sp)
                    }
                }
                Text(text = "Lesson Maker", fontSize = 24.sp)
            }
        }
        Divider(color = Color.Black, modifier = Modifier
            .padding(5.dp)
            .padding(horizontal = 15.dp)
            .padding(bottom = 5.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
    }
}