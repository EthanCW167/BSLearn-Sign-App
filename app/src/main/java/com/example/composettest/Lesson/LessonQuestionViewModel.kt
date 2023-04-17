package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.model.signData
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Domain.util.OrderType
import com.example.composettest.Domain.util.QuestionOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LessonQuestionViewModel @Inject constructor(
    private val lessonUseCases: LessonUseCases,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _questions = mutableStateOf(QuestionState())
    val questions_state: State<QuestionState> = _questions

    var sign_data: signData? = null

    private var getQuestionsJob: Job? = null

    init {
        savedStateHandle.get<Int>("lessonId")?.let { lessonId ->
            if(lessonId != -1) {
                getQuestionsByLessonId(QuestionOrder.orderNum(OrderType.Descending), lessonId = lessonId)
            }
        }
    }

    private fun getQuestionsByLessonId(questionOrder: QuestionOrder, lessonId: Int){
        getQuestionsJob?.cancel()
        lessonUseCases.getQuestionsByIdUseCase(lessonId)?.onEach { questions ->
            _questions.value = questions_state.value.copy(questions = questions, questionOrder = questionOrder)
        }
            ?.launchIn(viewModelScope)
    }

    suspend fun getSignDataBySignId(signId: Int){
        viewModelScope.launch {
            lessonUseCases.getSignDataByIdUseCase(signId)?.also { signData ->
                sign_data = signData
            }


        }
    }
}