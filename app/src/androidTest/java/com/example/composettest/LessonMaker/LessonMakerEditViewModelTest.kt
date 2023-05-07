package com.example.composettest.LessonMaker

import androidx.lifecycle.SavedStateHandle
import com.example.composettest.Domain.model.FQuestion
import com.example.composettest.Domain.model.FSignData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LessonMakerEditViewModelTest {

    private var lessonId = 1
    private var questionIndex = -1

    private lateinit var viewModel: LessonMakerEditViewModel

    @Before
    fun setUp() {
        viewModel = LessonMakerEditViewModel(savedStateHandle = SavedStateHandle())
    }

    @Test
    fun questionSet(){

        val defaultQuestion = FQuestion(questionId = 1)

        viewModel.questionSet(questionIndex)

        assert(viewModel.questionEdit.value.questionEdit == defaultQuestion)
    }

    @Test
    fun editTitle(){
        viewModel.onEvent(EditLessonEvent.EnteredTitle("Test Title"))

        assert(viewModel.lessonEditState.value.lesson.name == "Test Title")
    }

    @Test
    fun setQuestionType(){

        viewModel.questionSet(questionIndex)

        viewModel.onEvent(EditLessonEvent.SetQuestionType("Learn Sign"))

        assert(viewModel.questionEdit.value.questionEdit.questionType == "sign")

        viewModel.onEvent(EditLessonEvent.SetQuestionType("Multiple Choice"))

        assert(viewModel.questionEdit.value.questionEdit.questionType == "multiple_choice")
    }

    @Test
    fun setSignData(){

        val TestSign = FSignData()

        viewModel.questionSet(questionIndex)

        viewModel.signNameList.value.signNameList = listOf<String>(TestSign.sign)

        viewModel.signDataList.value.signDataList = listOf<FSignData>(TestSign)

        viewModel.onEvent(EditLessonEvent.SetSignData("Hello"))

        assert(viewModel.questionEdit.value.questionEdit.signData == TestSign)
    }

}