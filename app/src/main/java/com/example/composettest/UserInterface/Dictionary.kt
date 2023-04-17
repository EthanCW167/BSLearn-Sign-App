package com.example.composettest.UserInterface

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composettest.R
import com.example.composettest.Screen
import com.example.composettest.Domain.model.signData


@Composable
fun Dictionary (navController: NavController) {
    val viewModel = viewModel<SearchViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
            Text(text = "Dictionary", fontSize = 24.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
        }
        Divider(color = Color.Black, modifier = Modifier
            .padding(16.dp)
            .height(1.dp)
            .fillMaxWidth()
        )
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
                    items(persons) { person ->
                        Text(
                            text = "${person.firstName} ${person.lastName}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        )
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
        navController.navigate(Screen.SignView.withArgs(signData.filePath, signData.sign))
    },
        Modifier
            .padding(15.dp)
            .size(150.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(238,238,255), shape = RoundedCornerShape(20.dp))) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(imageVector = imageVector, contentDescription = description, Modifier.size(120.dp))
            Text(text = "$name", textAlign = TextAlign.Center)
        }
    }
}
