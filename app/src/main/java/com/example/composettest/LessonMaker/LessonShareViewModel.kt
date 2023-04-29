package com.example.composettest.LessonMaker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.User
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
class LessonShareViewModel @Inject constructor(

    SavedStateHandle: SavedStateHandle
): ViewModel(){

    val usersDb = Firebase.firestore.collection("users")
    val lessonsDB = Firebase.firestore.collection("lessons")

    private val _lessonState = mutableStateOf(LessonMakerSelectedState())
    val lessonState: MutableState<LessonMakerSelectedState> = _lessonState

    private val _usersState = mutableStateOf(SharedUsersState())
    val usersState: State<SharedUsersState> = _usersState

    var lessonIdGlobal: String = ""

    var lesson: FLesson = FLesson()

    init {
        SavedStateHandle.get<String>("lessonId")?.let {lessonId ->
            getLesson(lessonId = lessonId)
        }
    }

    fun getLesson(lessonId: String) = CoroutineScope(Dispatchers.IO).launch {

        lessonsDB.document(lessonId).get().addOnSuccessListener { document ->
            if (document != null){
                lesson = document.toObject<FLesson>()!!
                lessonIdGlobal = document.id
                _lessonState.value = lessonState.value.copy(lesson)
                getUsers(lessonId)
            }
        }
    }

    fun getUsers(lessonId: String) = CoroutineScope(Dispatchers.IO).launch {

        var users = mutableListOf<User>()

        for (userId in lessonState.value.lesson.sharedUserList)
            usersDb.document(userId).get().addOnSuccessListener { user ->
                if (user != null){
                    users.add(user.toObject<User>()!!)
                }
            }
        _usersState.value = usersState.value.copy(users)
    }

    fun onEvent(event: ShareLessonEvent){
        when(event){
            is ShareLessonEvent.AddUser -> {
                viewModelScope.launch {
                usersDb.document(event.userId).get().addOnSuccessListener { document ->
                    if (document != null) {

                        var user: User = document.toObject<User>()!!
                        user.sharedLesson.add(lessonIdGlobal)

                        var sharedUserList: MutableList<String> = lessonState.value.lesson.sharedUserList.toMutableList()
                        sharedUserList.add(event.userId)

                        var lesson: FLesson = lessonState.value.lesson
                        lesson.sharedUserList = sharedUserList

                        _lessonState.value = lessonState.value.copy(lesson)

                        viewModelScope.launch {
                            usersDb.document(event.userId).set(user).await()
                            lessonsDB.document(lessonIdGlobal).set(lessonState.value.lesson)
                        }
                    }
                }
                }
                getUsers(lessonIdGlobal)
            }
        }
    }
}

