package com.example.composettest.Lesson.LessonTest

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Lesson.QuestionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


    @HiltViewModel
    class LessonTestViewModel @Inject constructor(
        private val lessonUseCases: LessonUseCases,
        savedStateHandle: SavedStateHandle

    ): ViewModel() {

        var getOrderNum: Int? = null

        var getLessonId: Int? = null

        var getQuestionId: Int? = null

        var questionType: String? = null

        var signId: Int? = null

        var isCorrect: Int? = null

        init {
            savedStateHandle.get<Int>("lessonId")?.let { lessonId ->
                savedStateHandle.get<Int>("orderNum")?.let { orderNum ->
                    viewModelScope.launch {

                    lessonUseCases.getQuestionByIdByOrderUseCase(lessonId, orderNum)?.also { question ->
                        getOrderNum = question.orderNum
                        getLessonId = question.lessonId
                        getQuestionId = question.questionId
                        questionType = question.questionType
                        signId = question.signId
                        isCorrect = question.isCorrect
                    }
                }


            }
        }
        /*
        private fun getQuestionsByQuestionId(questionId: Int){
            getQuestionsJob?.cancel()
            lessonUseCases.getQuestionByQuestionIdUseCase(questionId = questionId).onEach { questions ->
            _questions.value = questions_state.value.copy(questions = questions)

        }

         */


    }
    }

