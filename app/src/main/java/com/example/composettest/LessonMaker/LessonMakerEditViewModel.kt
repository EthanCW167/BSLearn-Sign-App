package com.example.composettest.LessonMaker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.FQuestion
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
@HiltViewModel
class LessonMakerEditViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle
): ViewModel() {

    val lessonsDB = Firebase.firestore.collection("lessons")

    var lessonEdit: FLesson = FLesson()

    private val _lessonEditState = mutableStateOf(LessonMakerSelectedState())
    val lessonEditState: MutableState<LessonMakerSelectedState> = _lessonEditState

    private val _lessonName = mutableStateOf(LessonTitleState())
    val lessonName: State<LessonTitleState> = _lessonName

    init {
        savedStateHandle.get<String>("lessonId")?.let {lessonId ->
            if (lessonId != "1") {
                viewModelScope.launch {

                    lessonsDB.document(lessonId).get().addOnSuccessListener {
                            document ->
                        if (document != null) {
                            lessonEdit = document.toObject<FLesson>()!!
                            _lessonEditState.value = lessonEdit?.let { lessonEditState.value.copy(lesson = it) }!!
                            _lessonName.value = lessonName.value.copy(lessonEdit.name)
                        }
                    }
                }
            } else {
                lessonEdit = FLesson(questionsList = listOf(FQuestion()))
                _lessonEditState.value = lessonEditState.value.copy(lesson = lessonEdit)
            }
        }
    }

    fun onEvent(event: EditLessonEvent){
        when(event) {
            is EditLessonEvent.EnteredTitle -> {
                _lessonName.value = lessonName.value.copy(
                    name = event.value
                )
                lessonEditState.value.lesson.name = lessonName.value.name
            }
            is EditLessonEvent.SaveLesson -> {
                if (event.lessonId != "1") {
                    viewModelScope.launch {

                        lessonsDB.document(event.lessonId).set(lessonEditState.value.lesson)
                    }
                } else {
                    viewModelScope.launch {
                        lessonsDB.document().set(lessonEditState.value.lesson)
                    }
                }
            }
        }
    }
}





