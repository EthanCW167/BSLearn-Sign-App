package com.example.composettest.SharedLessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Lesson.LessonPreviewViewModel
import com.example.composettest.Lesson.SignChoiceViewModel
import com.example.composettest.Screen

@Composable
fun SharedLessonPreviewScreen(
    navController: NavController,
    viewModel: LessonPreviewViewModel = hiltViewModel(),
    signViewModel: SharedLessonPlayViewModel = hiltViewModel(),
    lessonId: String
){

    val title = signViewModel.lessonEditState.value.lesson.name
    val signs = signViewModel.lessonEditState.value.lesson.signs
    val questions = signViewModel.lessonEditState.value.lesson.questions
    val previewFilePath = signViewModel.lessonEditState.value.lesson.previewFilePath
    val description = signViewModel.lessonEditState.value.lesson.description

    println(signViewModel.lessonEditState.value.lesson.questionsList)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ) {}


    Column(
        Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.Start) {


            Button(
                onClick = { navController.navigateUp() },
                shape = RoundedCornerShape(60),
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 8.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(60)),
                colors = ButtonDefaults.buttonColors(Color.White)
            ) {
                Text(text = "Back", color = Color.Black, fontSize = 16.sp)
            }
        }

            if (title != null) {
                if (signs != null) {
                    if (questions != null) {
                        if (previewFilePath != null){
                            if (description != null){
                                //ImagePreview(previewFilePath)
                                PreviewDescription( name = title, signs = signs, questions = questions, description = description, navController = navController, viewModel = signViewModel, lessonId = lessonId)
                            }
                        }
                    }
                }
            }
        }
    }
@Composable
fun ImagePreview(previewFilePath: Int){

    Image(painter = painterResource(id = previewFilePath), contentDescription = "Lesson Image Preview", modifier = Modifier
        .height(300.dp)
        .width(350.dp)
        .padding(top = 50.dp)
        .padding(10.dp)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(Color.White))
}

@Composable
fun PreviewDescription(name: String, signs: Int, questions: Int, description: String, navController: NavController, viewModel: SharedLessonPlayViewModel, lessonId: String){
    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White, shape = RoundedCornerShape(20.dp))) {

        title(name)
        Divider(color = Color.Black, modifier = Modifier
            .padding(5.dp)
            .padding(horizontal = 15.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
        PreviewInfoBox(signs, questions)
        Text(text = "This is a community made lesson.", modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
            .fillMaxWidth(),
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(20.dp))
        Text(text = description, modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth())
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {

                if (viewModel.lessonEditState.value.lesson.questionsList[0].questionType == "sign") {
                navController.navigate(Screen.SharedLessonSignViewScreen.route + "?lessonId=${lessonId}&questionIndex=${0}&lessonTitle=${viewModel.lessonEditState.value.lesson.name}")
                } else if (viewModel.lessonEditState.value.lesson.questionsList[0].questionType == "multiple_choice") {
                navController.navigate(Screen.SharedLessonMultiChoiceScreen.route + "?lessonId=${lessonId}&questionIndex=${0}&lessonTitle=${viewModel.lessonEditState.value.lesson.name}")
            }} ,modifier = Modifier
                .width(200.dp)
                .height(70.dp)
                .padding(15.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(72,69,221)))  {
                Text(text = "Begin Lesson", color = Color.White)

            }
        }
    }
}

@Composable
fun title(name: String){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(text = name, fontSize = 22.sp, textAlign = TextAlign.Start)

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
            Text(text = "$questions Questions")
        }
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.WavingHand, contentDescription = "Signs")
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "+$signs Signs to learn")
        }

    }
}