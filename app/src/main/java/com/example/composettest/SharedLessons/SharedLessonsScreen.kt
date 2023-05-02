package com.example.composettest.SharedLessons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.LessonMaker.LessonIdState
import com.example.composettest.LessonMaker.LessonMakerState
import com.example.composettest.LessonMaker.LessonMakerViewModel
import com.example.composettest.Screen

@Composable
fun SharedLessonsScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry = navController.getBackStackEntry(Screen.HomeScreen.route),
    viewModel: SharedLessonsViewModel = hiltViewModel(remember { backStackEntry }),
    userId: String
){

    viewModel.getLessons()

    val state = viewModel.lessonState.value

    val idState = viewModel.lessonIdState.value



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}


    Column(Modifier.fillMaxSize()) {
        topBar(navController)
        MainBody(state = state, navController = navController, idState = idState, userId = userId, viewModel = viewModel)
    }
}


@Composable
fun topBar(navController: NavController){
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
                        onClick = { navController.navigate(Screen.HomeScreen.route) },
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

@Composable
fun MainBody(state: LessonMakerState, navController: NavController, idState: LessonIdState, userId: String, viewModel: SharedLessonsViewModel){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .padding(top = 12.dp)
        .clip(shape = RoundedCornerShape(20.dp))
        .background(Color.White, shape = RoundedCornerShape(20.dp))){

        Row(
            Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start) {
            Text(text = "Lessons", modifier = Modifier.padding(10.dp), fontSize = 24.sp)
        }
        Divider(color = Color.Black, modifier = Modifier
            .padding(5.dp)
            .padding(horizontal = 10.dp)
            .padding(bottom = 5.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
        Text(text = "This is the community lessons page, here you will see a list of lessons that have been shared to you by community members, feel free to take a lesson.",
            fontSize = 15.sp, modifier = Modifier
                .padding(15.dp)
                .height(80.dp))
        Divider(color = Color.Black, modifier = Modifier
            .padding(5.dp)
            .padding(horizontal = 10.dp)
            .padding(bottom = 5.dp)
            .height(1.dp)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(300.dp)){
            items(state.lessons) { lesson ->

                val index = state.lessons.indexOf(lesson)
                LessonCard(lesson, idState.lessonIds[index], navController = navController, userId = userId, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun LessonCard(lesson: FLesson, index: String, navController: NavController, userId: String, viewModel: SharedLessonsViewModel){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        .height(80.dp)
        .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))
        .clickable {navController.navigate(Screen.SharedLessonPreviewScreen.route + "?lessonId=${index}")},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(Modifier.padding()) {
            Text(text = lesson.name, modifier = Modifier
                .padding(bottom = 10.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Row() {
                    Icon(imageVector = Icons.Default.QuestionMark, contentDescription = "Question")
                    Text(text = "${lesson.questions} Questions", modifier = Modifier.padding(horizontal = 5.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row() {
                    Icon(imageVector = Icons.Default.WavingHand, contentDescription = "Signs")
                    Text(text = "+${lesson.signs} Signs to learn", modifier = Modifier.padding(horizontal = 5.dp))
                }

            }
        }
        Row() {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Edit", modifier = Modifier.padding(5.dp))
        }
    }
}