package com.example.composettest.Lesson

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.use_case.LessonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonPreviewViewModel @Inject constructor(
    private val lessonUseCases: LessonUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

     var lessonId: Int? = null

     var name: String? = null

     var lessonNum: Int? = null

     var signs: Int? = null

     var questions: Int? = null

    var isCompleted: Int? = null


    init {
        savedStateHandle.get<Int>("id")?.let {id ->
            if(id != -1) {
                viewModelScope.launch {
                    lessonUseCases.getLessonUseCase(id)?.also {lesson ->
                        lessonId = lesson.id
                        name = lesson.name
                        lessonNum = lesson.lessonNum
                        signs = lesson.signs
                        questions = lesson.questions
                        isCompleted = lesson.isCompleted
                    }
                }
            }
        }
    }

}