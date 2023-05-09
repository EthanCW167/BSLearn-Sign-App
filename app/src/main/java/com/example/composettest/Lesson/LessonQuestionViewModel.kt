package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.composettest.Domain.model.Question
import com.example.composettest.Domain.model.signData
import com.example.composettest.Domain.use_case.LessonUseCases
import com.example.composettest.Domain.util.QuestionOrder
import com.example.composettest.Screen
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

    var getOrderNum: Int? = null

    var getLessonId: Int? = null

    var getQuestionId: Int? = null

    var questionType: String? = null

    var signId: Int? = null

    var isCorrect: Int? = null

    var sign: String? = null

    var filePath: Int? = null

    var previewFilePath: Int? = null

    var nextQOrderNum: Int? = null

    var nextQId: Int? = null

    var nextQType: String? = null

    init {
        savedStateHandle.get<Int>("lessonId")?.let { lessonId ->
            savedStateHandle.get<Int>("orderNum")?.let { orderNum ->
                savedStateHandle.get<Int>("numQuestion")?.let { numQuestion ->

                    viewModelScope.launch {

                        lessonUseCases.getQuestionByIdByOrderUseCase(lessonId, orderNum)
                            ?.also { question ->
                                getOrderNum = question.orderNum
                                getLessonId = question.lessonId
                                getQuestionId = question.questionId
                                questionType = question.questionType
                                signId = question.signId
                                isCorrect = question.isCorrect

                            }

                        signId?.let {
                            lessonUseCases.getSignDataByIdUseCase(it)?.also { signData ->
                                sign = signData.sign
                                filePath = signData.filePath
                                previewFilePath = signData.previewFilePath
                            }
                        }

                        if (orderNum < numQuestion) {
                            lessonUseCases.getQuestionByIdByOrderUseCase(lessonId,orderNum+1)
                                ?.also { nextQuestion ->
                                    nextQId = nextQuestion.questionId
                                    nextQType = nextQuestion.questionType
                                    nextQOrderNum = nextQuestion.orderNum
                                }
                        }
                    }
                }


            }
        }

/*
        fun getSignDataBySignId(signId: Int) {
            viewModelScope.launch {
                lessonUseCases.getSignDataByIdUseCase(signId)?.also { signData ->
                    sign_data = signData
                }


            }

 */


    }


    fun getQuestionRedo(lessonId: Int, orderNum: Int, numQuestion: Int) {
        viewModelScope.launch {

            lessonUseCases.getQuestionByIdByOrderUseCase(lessonId, orderNum)?.also { question ->
                getOrderNum = question.orderNum
                getLessonId = question.lessonId
                getQuestionId = question.questionId
                questionType = question.questionType
                signId = question.signId
                isCorrect = question.isCorrect

                println(signId)
            }
            signId?.let {
                lessonUseCases.getSignDataByIdUseCase(it)?.also { signData ->
                    sign = signData.sign
                    filePath = signData.filePath
                    previewFilePath = signData.previewFilePath
                }
            }
            if (orderNum < numQuestion) {
                lessonUseCases.getQuestionByIdByOrderUseCase(lessonId,orderNum+1)
                    ?.also { nextQuestion ->
                        nextQId = nextQuestion.questionId
                        nextQType = nextQuestion.questionType
                        nextQOrderNum = nextQuestion.orderNum
                    }
            }
        }
    }

    fun updateQuestion(isCorrect: Int){
        viewModelScope.launch {

            getQuestionId?.let{it ->
                getOrderNum?.let { it1 ->
                    questionType?.let { it2 ->
                        signId?.let { it3 ->
                            Question(
                                questionId = it,
                                orderNum = it1,
                                lessonId = getLessonId,
                                questionType = it2,
                                signId = it3,
                                isCorrect = isCorrect
                            )
                        }
                    }
                }
            }?.let { it4 ->
                lessonUseCases.addQuestionUseCase(
                    it4
                )
            }
        }
        println("completed")
    }

    fun nextScreen(lessonId: Int, orderNum: Int, numQuestion: Int, navController: NavController, lessonTitle: String) {

        println("orderNum" + orderNum)
        println("numQuestion" + numQuestion)
        if (orderNum < numQuestion) {
            println(nextQType)

            if (nextQType == "multiple_choice") {
                navController.navigate(Screen.LessonQuestionMultiChoiceScreen.route + "?lessonId=${getLessonId}&orderNum=${nextQOrderNum}&numQuestion=${numQuestion}&lessonTitle=${lessonTitle}")
            } else if (nextQType == "sign") {
                navController.navigate(Screen.LessonSignViewScreen.route + "?lessonId=${getLessonId}&orderNum=${nextQOrderNum}&numQuestion=${numQuestion}&lessonTitle=${lessonTitle}")
            }
        } else {
            navController.navigate(Screen.LessonSummaryScreen.route + "?lessonId=${lessonId}&lessonTitle=${lessonTitle}")
        }
    }
}






