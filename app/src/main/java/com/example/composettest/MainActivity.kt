package com.example.composettest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.Lesson
import com.example.composettest.UserInterface.HomeScreen
import com.example.composettest.ui.theme.ComposetTestTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //val lessonsDB = Firebase.firestore.collection("lessons")

    var lessons: MutableList<FLesson> = emptyList<FLesson>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
            //retrieveLessons()

        }
    }

/*
    fun retrieveLessons() = CoroutineScope(Dispatchers.IO).launch {
        val querySnapshot = lessonsDB.get().await()


        for (document in querySnapshot.documents){
            val lesson = document.toObject<FLesson>()
            if (lesson != null) {
                lessons.add(lesson)
            }
        }
        println(lessons[0].description)
        println(lessons.size)
        println(lessons[0].questionsList[0].signData.sign)
    }

 */
}



/*

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposetTestTheme {
        Greeting("Android")
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
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(72,69,211), shape = RoundedCornerShape(20.dp)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            ) {

        Icon(Icons.Default.Fireplace, contentDescription = "Streak Icon",
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
            .align(CenterVertically)
        )

        Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Signs", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "5", fontSize = 18.sp) // TODO: Placeholder, replace with variable to get Signs
        }

        Icon(Icons.Default.WavingHand, contentDescription = "Streak Icon",
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
fun MainBody(list: List<TestData>){
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
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(238,238,255), shape = RoundedCornerShape(20.dp))

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
                ) {
                    items(
                        items = list
                    ) { item ->

                        LessonCard(testData = item) {

                        }
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
                        name = "Practice"
                    ) {

                    }
                    MainBodyIconButton(
                        imageVector = Icons.Default.CoffeeMaker,
                        description = "Lesson Maker",
                        name = "Lesson Maker"
                    ) {

                    }
                }
                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {

                    MainBodyIconButton(
                        imageVector = Icons.Rounded.List,
                        description = "Practice",
                        name = "Dictionary"
                    ) {

                    }
                    MainBodyIconButton(
                        imageVector = Icons.Default.PeopleOutline,
                        description = "Community",
                        name = "Community"
                    ) {

                    }
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.TopCenter)) {
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
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
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


@Composable
fun LessonCard(testData: TestData, onItemClick: () -> Unit){
    Card(
        Modifier
            .size(90.dp)
            .padding(10.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = onItemClick
            ),
        backgroundColor = Color.LightGray,
        elevation = 12.dp



    ) {
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
                Text(text = testData.lessonNum, textAlign = TextAlign.Center, color = Color.White, fontSize = 26.sp)
            }
            Text(text = testData.title, textAlign = TextAlign.Center)
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
                .background(Color(238,238,255)),
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
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.TopCenter)) {
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

 */
