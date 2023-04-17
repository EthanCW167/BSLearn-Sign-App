package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val lessonUseCases: LessonUseCases
) : ViewModel(){

    private val _state = mutableStateOf(LessonState())
    val state: State<LessonState> = _state

    private var getLessonsJob: Job? = null

    init {
        getLessons(LessonOrder.LessonNum(OrderType.Descending))
    }

    private fun getLessons(lessonOrder: LessonOrder){
        getLessonsJob?.cancel()
        lessonUseCases.getLessonsUseCase(lessonOrder)
            .onEach { lessons ->
             _state.value = state.value.copy(lessons = lessons, lessonOrder = lessonOrder)
        }
            .launchIn(viewModelScope)
}
}