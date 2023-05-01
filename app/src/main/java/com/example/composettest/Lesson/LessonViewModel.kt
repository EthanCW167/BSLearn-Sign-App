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

    val usersDB = Firebase.firestore.collection("users")

    private val _state = mutableStateOf(LessonState())
    val state: State<LessonState> = _state

    private val _user = mutableStateOf(UserState())
    val user: State<UserState> = _user

    private var getLessonsJob: Job? = null

    init {
        getLessons(LessonOrder.LessonNum(OrderType.Ascending))
    }

    private fun getLessons(lessonOrder: LessonOrder) {
        getLessonsJob?.cancel()
        lessonUseCases.getLessonsUseCase(lessonOrder)
            .onEach { lessons ->
                _state.value = state.value.copy(lessons = lessons, lessonOrder = lessonOrder)
            }
            .launchIn(viewModelScope)
    }

    fun getUserData(userId: String){
        viewModelScope.launch {
            val userSnapshot = usersDB.document(userId).get().await()
            _user.value  = user.value.copy(user = userSnapshot.toObject<User>()!!)
        }
    }
}