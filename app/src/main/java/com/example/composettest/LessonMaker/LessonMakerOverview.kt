package com.example.composettest.LessonMaker

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Screen

@Composable
fun LessonMakerOverview(
    navController: NavController,
    viewModel: LessonMakerViewModel = hiltViewModel(),
    userId: String
){
    viewModel.getLessons(userId)

    val state = viewModel.lessonState.value

    val idState = viewModel.lessonIdState.value



    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}


    Column(Modifier.fillMaxSize()) {
        topBar(navController)
        MainBody(state = state, navController = navController, idState = idState, userId = userId)
    }
}


@Composable
fun topBar(navController: NavController){
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Button(onClick = {navController.navigateUp()}) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = "Lesson Maker", fontSize = 24.sp)
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
fun MainBody(state: LessonMakerState, navController: NavController, idState: LessonIdState, userId: String){

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
        Text(text = "With this feature you can create your own lessons. Choose from a wide variety of signs and question types. you can also share your lessons with other people, just press the share button.",
            fontSize = 14.sp, modifier = Modifier
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
                LessonCard(lesson, idState.lessonIds[index], navController = navController, userId = userId)
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
                .background(Color(72,69,221))
                .clickable{navController.navigate(Screen.LessonMakerEditScreen.route + "?lessonId=${1}&userId=${userId}")},
                contentAlignment = Alignment.Center
                ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "new lesson",
                    modifier = Modifier
                        .size(40.dp)
                )
            }
        }

    }
}

@Composable
fun LessonCard(lesson: FLesson, index: String, navController: NavController, userId: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        .height(80.dp)
        .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))
        .clickable{navController.navigate(Screen.LessonMakerEditScreen.route + "?lessonId=${index}&userId=${userId}")},
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(Modifier.padding()) {
            Text(text = lesson.name, modifier = Modifier
                .padding(bottom = 10.dp), fontSize = 18.sp)
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
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.padding(5.dp))
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share", modifier = Modifier.padding(5.dp).clickable { navController.navigate(route = Screen.LessonShareScreen.route + "?lessonId=${index}") })
        }
    }
}