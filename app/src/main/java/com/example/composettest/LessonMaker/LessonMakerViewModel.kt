package com.example.composettest.LessonMaker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.Lesson
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class LessonMakerViewModel @Inject constructor(

    SavedStateHandle: SavedStateHandle

): ViewModel(){

    val lessonsDB = Firebase.firestore.collection("lessons")


    private val _lessonState = mutableStateOf(LessonMakerState())
    val lessonState: State<LessonMakerState> = _lessonState

    private val _lessonIdState = mutableStateOf(LessonIdState())
    val lessonIdState: State<LessonIdState> = _lessonIdState

    var lessonsId: MutableList<String> = emptyList<String>().toMutableList()


    init {
        SavedStateHandle.get<Int>("userId")?.let {userId ->
            getLessons(userId)
        }
    }

    private fun getLessons(author: Int) = CoroutineScope(Dispatchers.IO).launch{

        val querySnapshot = lessonsDB.whereEqualTo("author", author).get().await()

        var lessons: MutableList<FLesson> = emptyList<FLesson>().toMutableList()

        for (document in querySnapshot.documents){

            lessonsId.add(document.id)
            val lesson = document.toObject<FLesson>()
            if (lesson != null) {

                lessons.add(lesson)
            }
        }
        _lessonIdState.value = lessonIdState.value.copy(lessonIds = lessonsId)
        _lessonState.value = lessonState.value.copy(lessons = lessons)
    }



}