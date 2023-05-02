package com.example.composettest.SharedLessons

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.FSignData
import com.example.composettest.Domain.model.Question
import com.example.composettest.LessonMaker.LessonMakerSelectedState
import com.example.composettest.LessonMaker.LessonTitleState
import com.example.composettest.LessonMaker.SignDataState
import com.example.composettest.LessonMaker.SignNameListState
import com.example.composettest.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SharedLessonPlayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val lessonsDB = Firebase.firestore.collection("lessons")

    var signDataDB = Firebase.firestore.collection("signData")

    var lessonEdit: FLesson = FLesson()

    private val _lessonEditState = mutableStateOf(LessonMakerSelectedState())
    val lessonEditState: MutableState<LessonMakerSelectedState> = _lessonEditState

    private val _lessonName = mutableStateOf(LessonTitleState())
    val lessonName: State<LessonTitleState> = _lessonName

    private val _signNameList = mutableStateOf(SignNameListState())
    val signNameList: State<SignNameListState> = _signNameList

    private val _signDataList = mutableStateOf(SignDataState())
    val signDataList: State<SignDataState> = _signDataList

    init {
        savedStateHandle.get<String>("lessonId")?.let { lessonId ->
            println(lessonId)
            println(lessonId)
            println(lessonId)
            println(lessonId)
            println(lessonId)
            if (lessonId != "1") {
                viewModelScope.launch {

                    lessonsDB.document(lessonId).get().addOnSuccessListener { document ->
                        if (document != null) {
                            lessonEdit = document.toObject<FLesson>()!!
                            _lessonEditState.value =
                                lessonEdit?.let { lessonEditState.value.copy(lesson = it) }!!
                            _lessonName.value = lessonName.value.copy(lessonEdit.name)
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            val querySnapshot = signDataDB.get().await()

            var signDataArray: MutableList<FSignData>  = emptyList<FSignData>().toMutableList()

            var signNames: MutableList<String> = emptyList<String>().toMutableList()

            for (document in querySnapshot.documents){

                val signData = document.toObject<FSignData>()
                if (signData != null) {
                    signDataArray.add(signData)
                    signNames.add(signData.sign)
                }
            }
            _signDataList.value = signDataList.value.copy(signDataArray)
            _signNameList.value = signNameList.value.copy(signNames)
        }
    }

    fun nextScreen(questionIndex: Int, name: String, navController: NavController, lessonName: String) {

        if (questionIndex <= lessonEditState.value.lesson.questionsList.size -1) {

            if (lessonEditState.value.lesson.questionsList[questionIndex].questionType == "multiple_choice") {
                navController.navigate(Screen.SharedLessonMultiChoiceScreen.route + "?lessonId=${lessonName}&questionIndex=${questionIndex}&lessonTitle=${name}")
            } else if (lessonEditState.value.lesson.questionsList[questionIndex].questionType == "sign") {
                navController.navigate(Screen.SharedLessonSignViewScreen.route + "?lessonId=${lessonName}&questionIndex=${questionIndex}&lessonTitle=${name}")
            }
        }
        else {
            navController.navigate(Screen.SharedLessonSummaryScreen.route + "?lessonTitle=${name}")
        }
    }

    fun updateQuestion(questionIndex: Int, isCorrect: Int){

        var lesson = _lessonEditState.value.lesson

        println(isCorrect)
        println(isCorrect)
        if (isCorrect == 1){
            lesson.questionsList[questionIndex].isCorrect = 1
        } else {
            lesson.questionsList[questionIndex].isCorrect = 0
        }

        _lessonEditState.value = lessonEditState.value.copy(lesson)
        println(_lessonEditState.value.lesson.questionsList[questionIndex].isCorrect)
    }
}