package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.signData
import com.example.composettest.Domain.use_case.LessonUseCases
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

    /*
    init {
        savedStateHandle.get<Int>("lessonId")?.let { lessonId ->
            savedStateHandle.get<Int>("orderNum")?.let { orderNum ->
                getQuestionRedo(lessonId, orderNum)
                /*
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
                }

                 */


            }
        }

    /*
    fun getSignDataBySignId(signId: Int){
        viewModelScope.launch {
            lessonUseCases.getSignDataByIdUseCase(signId)?.also { signData ->
                sign_data = signData
            }


        }

     */
    }

     */

    fun getQuestionRedo(lessonId: Int, orderNum: Int){
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
            }
    }
}