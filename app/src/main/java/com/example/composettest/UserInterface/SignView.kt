

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.composettest.Screen


@Composable
fun SignView (navController: NavController, filepath: Int, sign: String){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        topBarDictionarySign(navController = navController)

        VideoDisplay(filepath = filepath)

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
                Text(modifier = Modifier.padding(20.dp), text = sign, fontSize = 24.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun topBarDictionarySign(navController: NavController){
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
                        onClick = { navController.navigateUp() },
                        shape = RoundedCornerShape(60),
                        modifier = Modifier
                            .width(100.dp)
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(60)),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Back" , color = Color.Black, fontSize = 16.sp)
                    }
                }
                Text(text = "Dictionary", fontSize = 24.sp)
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
fun VideoDisplay(filepath: Int){
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
