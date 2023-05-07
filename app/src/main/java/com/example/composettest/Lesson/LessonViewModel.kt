package com.example.composettest.Lesson

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composettest.Domain.model.User
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType
import com.example.composettest.UserInterface.UserState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val lessonUseCases: LessonUseCases
) : ViewModel(){

    val usersDB = Firebase.firestore.collection("users") // Instance of Firestore users collection

    private val _state = mutableStateOf(LessonState())
    val state: State<LessonState> = _state // Expose list of curated lessons to UI

    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user // Expose user data to UI

    private var getLessonsJob: Job? = null

    init { // On instantiation of view model
        getLessons(LessonOrder.LessonNum(OrderType.Ascending)) // return lessons in ascending order
    }

    private fun getLessons(lessonOrder: LessonOrder) {
        getLessonsJob?.cancel()
        lessonUseCases.getLessonsUseCase(lessonOrder) // Get from database
            .onEach { lessons ->
                _state.value = state.value.copy(lessons = lessons, lessonOrder = lessonOrder)
            }
            .launchIn(viewModelScope) // Place database onto alternative thread
    }

    fun getUserData(userId: String){ // Return users data
        viewModelScope.launch {// Place database onto alternative thread
            val userSnapshot = usersDB.document(userId).get().await()
            _user.value  = user.value.copy(user = userSnapshot.toObject<User>()!!)
        }
    }
}