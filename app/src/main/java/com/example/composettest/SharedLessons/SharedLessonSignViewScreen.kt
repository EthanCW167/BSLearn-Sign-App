package com.example.composettest.SharedLessons

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composettest.Lesson.LessonQuestionViewModel
import com.example.composettest.Lesson.topBarLesson
import com.example.composettest.Screen

@Composable
fun SharedLessonSignViewScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry = navController.getBackStackEntry(Screen.SharedLessonPreviewScreen.route + "?lessonId={lessonId}"),
    signViewModel: SharedLessonPlayViewModel = hiltViewModel(remember { backStackEntry }),
    lessonId: String,
    questionIndex: Int,
    lessonTitle: String
){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        topBarLesson(navController = navController, lessonTitle = lessonTitle)

        signViewModel.lessonEditState.value.lesson.questionsList[questionIndex].signData.filePath?.let { VideoDisplay(filepath = it) }

        println(signViewModel.lessonEditState.value.lesson.questionsList.size)

        Column(
            Modifier
                .padding(top = 10.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp))) {

            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))){
                signViewModel.lessonEditState.value.lesson.questionsList[questionIndex].signData.sign?.let { Text(modifier = Modifier.padding(20.dp), text = it, fontSize = 24.sp, textAlign = TextAlign.Center) }
            }
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {signViewModel.nextScreen(questionIndex = questionIndex+1 ,signViewModel.lessonEditState.value.lesson.name, navController, lessonName = lessonId)} ,modifier = Modifier
                    .width(200.dp)
                    .height(70.dp)
                    .padding(15.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(72,69,221)))  {
                    Text(text = "Next", color = Color.White)

                }
            }
        }
    }
}

@Composable
private fun VideoDisplay(filepath: Int){
    val context = LocalContext.current
    val exoPlayer = remember { getSimpleExoPlayer(context, filepath) }
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .padding(top = 15.dp)
            .clip(shape = RoundedCornerShape(20.dp)),
        factory = { context1 ->
            PlayerView(context1).apply {
                player = exoPlayer
            }
        },
    )
}



@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun  getSimpleExoPlayer(context: Context, filepath: Int): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {

        val uri = "android.resource://" + context.packageName + "/" + filepath

        val defaultDataSourceFactory = DefaultDataSource.Factory(context)
        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
            context,
            defaultDataSourceFactory
        )
        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(uri))
        setMediaSource(source)
        prepare()
        play()
        repeatMode = Player.REPEAT_MODE_ALL
    }
}

