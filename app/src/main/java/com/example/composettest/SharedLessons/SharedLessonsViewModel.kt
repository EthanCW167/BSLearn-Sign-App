package com.example.composettest.SharedLessons

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.User
import com.example.composettest.LessonMaker.LessonIdState
import com.example.composettest.LessonMaker.LessonMakerState
import com.example.composettest.UserInterface.UserState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class SharedLessonsViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle
): ViewModel() {


    val userDB = Firebase.firestore.collection("users")

    val lessonsDB = Firebase.firestore.collection("lessons")

    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user

    private val _lessonState = mutableStateOf(LessonMakerState())
    val lessonState: State<LessonMakerState> = _lessonState

    private val _lessonIdState = mutableStateOf(LessonIdState())
    val lessonIdState: State<LessonIdState> = _lessonIdState

/*
    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            viewModelScope.launch {
                getUser(userId)
                getLessons()
            }
        }
    }

 */

    fun getUser(userId: String){
        viewModelScope.launch {
            val userSnapshot = userDB.document(userId).get().await()
            _user.value  = user.value.copy(user = userSnapshot.toObject<User>()!!)
            //println(user.value.user.sharedLesson.size)
        }
        //println(user.value.user.sharedLesson.size)

    }

    fun getLessons(){
        viewModelScope.launch {

            println(user.value.user.sharedLesson.size)
            var lessonList: MutableList<FLesson> = mutableListOf()
            var lessonsId: MutableList<String> = mutableListOf()

            for (lessonId in user.value.user.sharedLesson) {
                val lessonSnapshot = lessonsDB.document(lessonId).get().await()

                lessonList.add(lessonSnapshot.toObject<FLesson>()!!)
                lessonsId.add(lessonSnapshot.id)
            }

            _lessonIdState.value = lessonIdState.value.copy(lessonIds = lessonsId)
            _lessonState.value = lessonState.value.copy(lessonList)
        }
    }
}
