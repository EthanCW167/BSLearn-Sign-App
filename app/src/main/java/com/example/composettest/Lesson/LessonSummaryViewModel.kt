package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Domain.util.LessonOrder
import com.example.composettest.Domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonSummaryViewModel @Inject constructor(
    private val lessonUseCases: LessonUseCases,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _qState = mutableStateOf(QuestionState())
    val qState: State<QuestionState> = _qState

    private var getQuestionsJob: Job? = null

    init {
        savedStateHandle.get<Int>("lessonId")?.let { lessonId ->
            getQuestions(lessonId)
        }
    }

    private fun getQuestions(lessonId: Int){
        getQuestionsJob?.cancel()
        lessonUseCases.getQuestionsByIdUseCase(lessonId)
            ?.onEach { questions ->
                _qState.value = qState.value.copy(questions = questions)
            }
            ?.launchIn(viewModelScope)
    }}





