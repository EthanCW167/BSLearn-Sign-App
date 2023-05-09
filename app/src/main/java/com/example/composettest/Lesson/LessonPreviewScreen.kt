package com.example.composettest.Lesson

import android.graphics.drawable.Drawable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.R
import com.example.composettest.Screen

@Composable
fun LessonPreviewScreen(
    navController: NavController,
    viewModel: LessonPreviewViewModel = hiltViewModel(),
    signViewModel: SignChoiceViewModel = hiltViewModel(),
    id: Int
){



    val title = viewModel.name
    val lessonNum = viewModel.lessonNum
    val signs = viewModel.signs
    val questions = viewModel.questions
    val isCompleted = viewModel.isCompleted
    val previewFilePath = viewModel.previewFilePath
    val description = viewModel.description
    //val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ) {}


    Column(Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.Start) {


            Button(
                onClick = { navController.navigateUp()
                 },
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

        if (lessonNum != null) {
            if (title != null) {
                if (signs != null) {
                    if (questions != null) {
                        if (previewFilePath != null){
                            if (description != null){
                                ImagePreview(previewFilePath)
                                PreviewDescription(id = id, name = title, lessonNum = lessonNum, signs = signs, questions = questions, description = description, navController = navController, viewModel = viewModel)
                            }
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
fun PreviewDescription(id: Int, name: String, lessonNum: Int, signs: Int, questions: Int, description: String, navController: NavController, viewModel: LessonPreviewViewModel){
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
            Button(onClick = {navController.navigate(Screen.LessonSignViewScreen.route + "?lessonId=${id}&orderNum=${1}&numQuestion=${signs + questions}&lessonTitle=${name}")} ,modifier = Modifier
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
            Text(text = "$questions Questions")
        }
        Row(horizontalArrangement = Arrangement.Start) {
            Icon(imageVector = Icons.Default.WavingHand, contentDescription = "Signs")
            Spacer(modifier = Modifier.size(5.dp))
            Text(text = "+$signs Signs to learn")
        }

    }
}
