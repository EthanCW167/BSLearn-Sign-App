package com.example.composettest.Practice

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.FSignData
import com.example.composettest.Domain.model.signData
import com.example.composettest.R
import com.example.composettest.Screen
import com.example.composettest.UserInterface.SearchViewModel

@Composable
fun PracticeSelectionScreen(
    navController: NavController
){
    val viewModel: PracticeViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val signDataList by viewModel.searchList.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val practiceLesson = remember {
        mutableStateOf(viewModel.lesson.value)
    }

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        
        topBarPractice(navController = navController)

        WordsAdded(wordsAdded = practiceLesson.value.lesson.questionsList.size)

        Row() {

            TextField(
                value = searchText,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onValueChange = viewModel::onSearchTextChange,

                placeholder = {
                    Row() {

                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                        Text(text = "Search")
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp))) {


            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    println("here")
                    items(signDataList) { signData ->
                        /*
                        Text(
                            text = signData.sign,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )

                         */
                        SignPracticeCard(signData = signData, navController = navController, viewModel = viewModel)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        if (practiceLesson.value.lesson.questionsList.isNotEmpty()) {
                        navController.navigate(Screen.PracticeSignView.route + "?questionIndex=${0}")
                        } else {
                            Toast
                                .makeText(context, "No signs selected", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                        modifier = Modifier
                            .width(200.dp)
                            .height(60.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(72, 69, 221))) {
                        Text(text = "Begin Practice", color = Color.White, fontSize = 20.sp)

                    }

                }
            }
        }
    }
}

@Composable
fun SignPracticeCard(signData: FSignData, navController: NavController, viewModel: PracticeViewModel) {
    println(R.raw.sample1)
    println(R.raw.sample2)
    println(2131623936)
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable {
                viewModel.addSign(signData)
                viewModel.onSearchTextChange("")
            },
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = signData.previewFilePath),
            contentDescription = "Question Preview",
            modifier = Modifier
                .height(140.dp)
                .width(180.dp)
                .padding(5.dp)
                .background(Color.White, shape = RoundedCornerShape(20.dp))
                .border(border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(20.dp))
        )
        Row(
            Modifier
                .width(160.dp)
                .height(70.dp)
                .background(
                    Color(238, 238, 255), shape = RoundedCornerShape(20.dp)
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = signData.sign, fontSize = 20.sp)
        }
    }

}

@Composable
fun WordsAdded(
    wordsAdded: Int
){
    Box(modifier = Modifier
        .padding(top = 25.dp)
        .size(45.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(13.dp))
        .background(Color.White, shape = RoundedCornerShape(13.dp)), contentAlignment = Alignment.Center){
        Text(text = "${wordsAdded/2}", fontSize = 26.sp, fontWeight = FontWeight.Bold)
    }
    Text(text = "Words Added", fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(top = 5.dp))

}

@Composable
fun topBarPractice(navController: NavController){
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
                        modifier = Modifier
                            .width(100.dp)
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(60)),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Back" , color = Color.Black, fontSize = 16.sp)
                    }
                }
                Text(text = "Practice", fontSize = 24.sp)
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