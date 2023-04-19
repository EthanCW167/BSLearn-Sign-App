package com.example.composettest.UserInterface

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.composettest.Domain.model.Lesson
import com.example.composettest.Lesson.LessonState
import com.example.composettest.Lesson.LessonViewModel
import com.example.composettest.R
import com.example.composettest.Screen
import com.example.composettest.ui.theme.ComposetTestTheme

@Composable
fun HomeScreen(navController: NavController, viewModel: LessonViewModel = hiltViewModel()) {

    val state = viewModel.state.value

    ComposetTestTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(72,69,221)
        ) {}
    }

    Column (horizontalAlignment = Alignment.CenterHorizontally) {

        TopBar()

        MainBody(createTempDataList(), navController = navController, state)
    }
}


@Composable
fun FirstButton(onClick: () -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Button(onClick = onClick) {
            Text(text = "Click Me")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column() {
            Text(text = "test")
            Text(text = "Down")
        }
    }
}
@Preview
@Composable
fun InfoBox() {
    Row(
        Modifier
            .padding(top = 100.dp)
            .width(350.dp)
            .height(70.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(72, 69, 211), shape = RoundedCornerShape(20.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {

        Icon(
            Icons.Default.Fireplace, contentDescription = "Streak Icon",
            Modifier
                .padding(top = 5.dp)
                .size(32.dp))
        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Streak", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "5", fontSize = 18.sp) // TODO: Placeholder, replace with variable to get Streaks
        }

        Divider(color = Color.Black, modifier = Modifier
            .height(50.dp)
            .width(1.dp)
            .align(Alignment.CenterVertically)
        )

        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Signs", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "5", fontSize = 18.sp) // TODO: Placeholder, replace with variable to get Signs
        }

        Icon(
            Icons.Default.WavingHand, contentDescription = "Streak Icon",
            Modifier
                .padding(top = 5.dp)
                .size(32.dp))
    }
}
//Modifier.height(IntrinsicSize.Min) horizontalArrangement = Arrangement.SpaceEvenly

@Preview
@Composable
fun TopBar() {
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar(
        title = {
            Text(text = "Sign App")
        },
        actions = {
            // search icon
            TopBarIconButton(
                imageVector = Icons.Outlined.Notifications,
                description = "Notifications",

                ) {
                Toast.makeText(contextForToast, "Notifications", Toast.LENGTH_SHORT)
                    .show()
            }

            // lock icon
            TopBarIconButton(
                imageVector = Icons.Outlined.Person,
                description = "Profile"
            ) {
                Toast.makeText(contextForToast, "Profile", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )
}

@Composable
fun TopBarIconButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit,
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun MainBody(list: List<TestData>, navController: NavController, state: LessonState){
    Box() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.White, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfoBox()
            Text(
                text = "Lessons",
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp), textAlign = TextAlign.Center, fontSize = 24.sp
            )

            Row(
                Modifier
                    .padding(horizontal = 10.dp)
                    .padding()
                    .fillMaxWidth()
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                    .height(120.dp)
                    .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))

                /*.border(
                    border = BorderStroke(width = 1.dp, color = Color.Black),
                    RoundedCornerShape(20.dp)
                )*/,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                LazyRow(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 5.dp)
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly

                ) {
                    items(state.lessons) { lesson ->

                        LessonCard(lessonData = lesson, navController = navController)

                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow", modifier = Modifier.size(40.dp))
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxSize(), horizontalArrangement = Arrangement.Center
            ) {
                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {

                    MainBodyIconButton(
                        imageVector = Icons.Default.MenuBook,
                        description = "Practice",
                        name = "Practice",
                        navController = navController
                    )
                    MainBodyIconButton(
                        imageVector = Icons.Default.CoffeeMaker,
                        description = "Lesson Maker",
                        name = "Lesson Maker",
                        navController = navController
                    )
                }
                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {

                    MainBodyIconButton(
                        imageVector = Icons.Rounded.List,
                        description = "Practice",
                        name = "Dictionary",
                        navController = navController
                    )
                    MainBodyIconButton(
                        imageVector = Icons.Default.PeopleOutline,
                        description = "Community",
                        name = "Community",
                        navController = navController
                    )
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(
            Alignment.TopCenter)) {
            ProfileCard()
            Text(text = "*Insert Name*", fontSize = 24.sp)
        }
    }
}


@Composable
fun MainBodyIconButton(
    imageVector: ImageVector,
    description: String,
    name: String,
    navController: NavController
) {
    IconButton(onClick = {
        navController.navigate(Screen.Dictionary.route)
    },
        Modifier
            .padding(15.dp)
            .size(150.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp))) {
        Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(imageVector = imageVector, contentDescription = description, Modifier.size(120.dp))
            Text(text = "$name", textAlign = TextAlign.Center)
        }
    }
}


@Composable
fun LessonCard(lessonData: Lesson, navController: NavController){
    Card(
        Modifier
            .size(90.dp)
            .padding(5.dp)
            .clickable {navController.navigate(
                Screen.LessonPreviewScreen.route + "?id=${lessonData.id}"
            )},
        backgroundColor = Color.LightGray) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(5.dp)
                    .size(40.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(
                        Color.Black, shape = RoundedCornerShape(10.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Text(text = "${lessonData.lessonNum}", textAlign = TextAlign.Center, color = Color.White, fontSize = 26.sp)
            }
            Text(text = lessonData.name, textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun ProfileCard(){

    Box(){
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(238, 238, 255)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Profile Placeholder", Modifier.size(60.dp))
        }
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Icon", modifier = Modifier
            .size(35.dp)
            .align(Alignment.BottomEnd))
    }
}

@Preview
@Composable
fun OverlapTest(){
    Box {

        Column(
            Modifier
                .fillMaxSize()
                .padding(5.dp, top = 60.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(Color.LightGray, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){}
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(
            Alignment.TopCenter)) {
            ProfileCard()
            Text(text = "*Insert Name*")
        }

    }
}


fun createTempDataList(): List<TestData> {

    val list = mutableListOf<TestData>()

    list.add(TestData("0","Greetings"))
    list.add(TestData("1","Hobbies"))
    list.add(TestData("2","Alphabet 1"))
    list.add(TestData("3","Alphabet 2"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))
    list.add(TestData("4","Miscellaneous"))

    val tempRandomList = mutableListOf<TestData>()
    for (i in list.indices) {
        tempRandomList.add(list.random())
    }

    return tempRandomList
}

data class TestData(
    val lessonNum: String,
    val title: String
)