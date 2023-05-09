package com.example.composettest.UserInterface

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FSignData
import com.example.composettest.Domain.model.Question
import com.example.composettest.R
import com.example.composettest.Screen
import com.example.composettest.Domain.model.signData


@Composable
fun Dictionary (navController: NavController) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsState()
    val signDataList by viewModel.searchList.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        topBarDictionary(navController = navController)
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
        Spacer(modifier = Modifier.height(32.dp))
        /*
        Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {

            val testSignData = signData(1,"Hello", R.raw.sample1, R.raw.sample1)
            BodyIconButton(
                imageVector = Icons.Rounded.List,
                description = "Practice",
                name = "Dictionary",
                signData = testSignData,
                navController = navController
            )
        }

         */
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
                        SignDictionaryCard(signData = signData, navController = navController)
                    }
                }
            }
        }
    }
}



@Composable
fun BodyIconButton(
    imageVector: ImageVector,
    description: String,
    name: String,
    signData: signData,
    navController: NavController

) {
    IconButton(onClick = {
        navController.navigate(Screen.SignView.route + "?filePath=${R.raw.sample1}&sign=${"Hello"}")
    },
        Modifier
            .padding(15.dp)
            .size(150.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(imageVector = imageVector, contentDescription = description, Modifier.size(120.dp))
            Text(text = "$name", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun SignDictionaryCard(signData: FSignData, navController: NavController) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { navController.navigate(Screen.SignView.route + "?filePath=${signData.filePath}&sign=${signData.sign}") },
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
                .clip(shape = RoundedCornerShape(20.dp))
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
fun topBarDictionary(navController: NavController){
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
