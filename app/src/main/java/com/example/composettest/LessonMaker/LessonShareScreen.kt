package com.example.composettest.LessonMaker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.User
import com.example.composettest.Screen

@Composable
fun LessonShareScreen(
    navController: NavController,
    viewModel: LessonShareViewModel = hiltViewModel(),
    lessonId: String
){

    val lessonState = viewModel.lessonState.value

    val usersState = viewModel.usersState.value

    viewModel.lessonIdGlobal = lessonId

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}


    Column(Modifier.fillMaxSize()) {
        topBar(navController)
        LessonCard(lesson = lessonState.lesson)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))) {

            searchUser()
            Divider(color = Color.Black, modifier = Modifier
                .padding(5.dp)
                .padding(horizontal = 10.dp)
                .height(1.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
            )
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)){
                items(usersState.Users) { user ->

                    var index = usersState.Users.indexOf(user)
                    UserCard(user = user, lessonState.lesson.sharedUserList[index])
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: FLesson){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp)
        .padding(top = 10.dp)
        .padding(horizontal = 20.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        .height(80.dp)
        .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp)),
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
    }
}

@Composable
fun searchUser(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(Color(238, 238, 255), shape = RoundedCornerShape(50.dp))) {

    }
}

@Composable
fun UserCard(user: User, userId: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .padding(bottom = 8.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))) {

        Text(text = user.name, textAlign = TextAlign.Start, modifier = Modifier.padding(horizontal = 10.dp))
        Text(text = userId, textAlign = TextAlign.End, modifier = Modifier.padding(horizontal = 10.dp))
    }

    }
