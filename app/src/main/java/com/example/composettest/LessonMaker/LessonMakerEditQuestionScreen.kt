package com.example.composettest.LessonMaker

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.*
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.composettest.Screen
import com.kanyidev.searchable_dropdown.SearchableExpandedDropDownMenu

@Composable
fun LessonMakerEditQuestionsScreen(
    navController: NavController,
    backStackEntry: NavBackStackEntry = navController.getBackStackEntry(Screen.LessonMakerEditScreen.route + "?lessonId={lessonId}&questionIndex={questionIndex}&userId={userId}"),
    viewModel: LessonMakerEditViewModel = hiltViewModel(remember { backStackEntry }),
    lessonId: String,
    questionIndex: Int,
    userId: String
){

    println(questionIndex)

    val state = viewModel.lessonEditState.value

    val lessonTitle = viewModel.lessonName.value

    val questionEdit = viewModel.questionEdit.value

    viewModel.questionSet(questionIndex = questionIndex)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(72,69,221)
    ){}
    
    
    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        
        topBarQuestionEdit(navController = navController)
  
        Image(painter = painterResource(id = questionEdit.questionEdit.signData.previewFilePath),
            contentDescription = "Sign Preview",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(shape = RoundedCornerShape(20.dp)))

        questionPreview(questionEdit = questionEdit)
        
        questionSetter(questionEdit = questionEdit ,navController, viewModel = viewModel, lessonId = lessonId, questionIndex = questionIndex)
    }
}

@Composable
fun questionPreview(questionEdit: QuestionEditState){
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(8.dp)
        .background(Color.White, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Chosen Sign", fontSize = 18.sp, modifier = Modifier.padding(top = 5.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .background(Color(238, 238, 255), shape = RoundedCornerShape(20.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
            Text(text = questionEdit.questionEdit.signData.sign, fontSize = 26.sp)
        }
    }
}

@Composable
fun questionSetter(questionEdit: QuestionEditState, navController: NavController, viewModel: LessonMakerEditViewModel, lessonId: String, questionIndex: Int){
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .background(Color(235, 235, 255))) {

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
        , horizontalAlignment = Alignment.CenterHorizontally) {


            Divider(
                color = Color.Black, modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(bottom = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Question Type", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Demo_ExposedDropdownMenuBox(viewModel = viewModel, questionEdit = questionEdit)
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Word to Learn", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = TextAlign.Center
            )

            var signs = listOf("hello", "goodbye")
            val context = LocalContext.current
            SearchableExpandedDropDownMenu(
                modifier = Modifier,
                listOfItems = viewModel.signNameList.value.signNameList,
                onDropDownItemSelected = { item ->
                    Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(EditLessonEvent.SetSignData(item))
                },
                dropdownItem = { name -> Text(name) })

            Spacer(modifier = Modifier.height(20.dp))
/*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(
                            EditLessonEvent.SaveQuestion(
                                lessonId = lessonId,
                                questionIndex = questionIndex
                            )
                        )
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Save Edit", fontSize = 22.sp)
            }


 */

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .width(160.dp)
                        .height(60.dp)
                        .padding(top = 10.dp)
                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                        .background(Color(72, 69, 221), shape = RoundedCornerShape(20.dp))
                        .clickable {
                            viewModel.onEvent(
                                EditLessonEvent.SaveQuestion(
                                    lessonId = lessonId,
                                    questionIndex = questionIndex
                                )
                            )
                            navController.navigateUp() // Check here after eating hungry shack
                                   },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(text = "Save Edit", fontSize = 22.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(imageVector = Icons.Default.SaveAlt, contentDescription = "Save Icon")
                }
            }
        }
        }
    }

@Composable
fun topBarQuestionEdit(navController: NavController){
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
                        modifier = Modifier.width(100.dp).shadow(elevation = 5.dp, shape = RoundedCornerShape(60)),
                        colors = ButtonDefaults.buttonColors(Color.White)
                    ) {
                        Text(text = "Back" , color = Color.Black, fontSize = 16.sp)
                    }
                }
                Text(text = "Lesson Maker", fontSize = 24.sp)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Demo_ExposedDropdownMenuBox(viewModel: LessonMakerEditViewModel, questionEdit: QuestionEditState ) {
    val context = LocalContext.current
    val questionTypes = arrayOf("Learn Sign", "Multiple Choice")
    var selectedText by remember { mutableStateOf(questionTypes[0]) }

    var initQType = questionEdit.questionEdit.questionType

    if (initQType == "sign"){
        selectedText = questionTypes[0]
    } else if (initQType == "multiple_choice"){
        selectedText = questionTypes[1]
    }

    var expanded by remember { mutableStateOf(false) }


    Box() {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                questionTypes.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            viewModel.onEvent(EditLessonEvent.SetQuestionType(item))
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}