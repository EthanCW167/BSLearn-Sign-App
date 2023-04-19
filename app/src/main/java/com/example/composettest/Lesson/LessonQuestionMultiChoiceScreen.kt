package com.example.composettest.Lesson

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController

@Composable
fun LessonQuestionMultiChoiceScreen (
    navController: NavController,
    viewModel: LessonQuestionViewModel = hiltViewModel(),
    orderNum: Int,
    lessonId: Int
){

    viewModel.getQuestionRedo(lessonId,orderNum)
    //println(viewModel.filePath)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Text(text = "Lesson Sign View", fontSize = 24.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
            lessonNumBox(lessonNum = 1)
        }
        Divider(color = Color.Black, modifier = Modifier
            .padding(16.dp)
            .height(1.dp)
            .fillMaxWidth()
        )
        viewModel.filePath?.let { VideoDisplay(filepath = it) }

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
                viewModel.sign?.let { Text(modifier = Modifier.padding(20.dp), text = it, fontSize = 24.sp, textAlign = TextAlign.Center) }
            }
            Column(modifier = Modifier
                .fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                   selectionButton(text = "Test")
                   selectionButton(text = "Test2")
                }


            }
        }
    }
}
@Composable
fun selectionButton(text: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Color.Blue else Color.Green

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {},
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text,
                color = Color.White
            )
        }
    }
}

@Composable
private fun VideoDisplay(filepath: Int){
    val context = LocalContext.current
    val exoPlayer = remember{ getSimpleExoPlayer(context, filepath) }
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
private fun getSimpleExoPlayer(context: Context, filepath: Int): ExoPlayer {
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