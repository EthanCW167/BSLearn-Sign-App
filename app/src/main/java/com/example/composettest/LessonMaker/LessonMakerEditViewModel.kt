package com.example.composettest.LessonMaker

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FLesson
import com.example.composettest.Domain.model.FQuestion
import com.example.composettest.Domain.model.FSignData
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

    var signDataDB = Firebase.firestore.collection("signData")

    var lessonEdit: FLesson = FLesson()

    private val _lessonEditState = mutableStateOf(LessonMakerSelectedState())
    val lessonEditState: MutableState<LessonMakerSelectedState> = _lessonEditState

    private val _lessonName = mutableStateOf(LessonTitleState())
    val lessonName: State<LessonTitleState> = _lessonName

    private val _signNameList = mutableStateOf(SignNameListState())
    val signNameList: State<SignNameListState> = _signNameList

    private val _signDataList = mutableStateOf(SignDataState())
    val signDataList: State<SignDataState> = _signDataList

    //var signData: FSignData = FSignData()

    private val _questionEdit = mutableStateOf(QuestionEditState())
    var questionEdit: MutableState<QuestionEditState> = _questionEdit

    init {
        //savedStateHandle.get<Int>("questionIndex")?.let { questionIndex ->
            //if (questionIndex == -1){
                savedStateHandle.get<String>("lessonId")?.let { lessonId ->
                    if (lessonId != "1") {
                        viewModelScope.launch {

                            lessonsDB.document(lessonId).get().addOnSuccessListener { document ->
                                if (document != null) {
                                    lessonEdit = document.toObject<FLesson>()!!
                                    _lessonEditState.value =
                                        lessonEdit?.let { lessonEditState.value.copy(lesson = it) }!!
                                    _lessonName.value = lessonName.value.copy(lessonEdit.name)
                                }
                            }
                        }
                    } else {
                        savedStateHandle.get<String>("userId")?.let { userId ->
                            lessonEdit =
                                FLesson(author = userId, questionsList = listOf(FQuestion()))
                            _lessonEditState.value = lessonEditState.value.copy(lesson = lessonEdit)
                        }
                    }
                }
            viewModelScope.launch {
                val querySnapshot = signDataDB.get().await()

                var signDataArray: MutableList<FSignData>  = emptyList<FSignData>().toMutableList()

                var signNames: MutableList<String> = emptyList<String>().toMutableList()

                for (document in querySnapshot.documents){

                    val signData = document.toObject<FSignData>()
                    if (signData != null) {
                        signDataArray.add(signData)
                        signNames.add(signData.sign)
                    }
                }
                _signDataList.value = signDataList.value.copy(signDataArray)
                _signNameList.value = signNameList.value.copy(signNames)
            }

    }

    fun questionSet(questionIndex: Int){
        if (questionIndex != -1) {
            _questionEdit.value = questionEdit.value
                .copy(lessonEditState.value.lesson.questionsList[questionIndex])
        } else {
            if (lessonEditState.value.lesson.questionsList.isEmpty()) {
                _questionEdit.value = questionEdit.value.copy(FQuestion())
            } else {
                _questionEdit.value = questionEdit.value.copy(FQuestion(
                    questionId = lessonEditState.value.lesson.questionsList[lessonEditState.value.lesson.questionsList.lastIndex].questionId + 1))
            }
        }
    }

    //fun setQuestionType(questionType: String){
   //     questionEdit.value.questionEdit.questionType = questionType
    //}

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

                        lessonsDB.document(event.lessonId).set(lessonEditState.value.lesson).await()
                    }
                } else {
                    viewModelScope.launch {
                        lessonsDB.document().set(lessonEditState.value.lesson).await()
                    }
                }
            }
            is EditLessonEvent.SetQuestionType -> {
                if (event.questionType == "Learn Sign"){
                    questionEdit.value.questionEdit.questionType = "sign"
                } else if (event.questionType == "Multiple Choice") {
                    questionEdit.value.questionEdit.questionType = "multiple_choice"
                }
            }
            is EditLessonEvent.SetSignData -> {

                var index = signNameList.value.signNameList.indexOf(event.signName)

               questionEdit.value.questionEdit.signData = signDataList.value.signDataList[index]
            }
            is EditLessonEvent.SaveQuestion -> {
                viewModelScope.launch {
                    var questionList = mutableListOf<FQuestion>()
                    for(question in lessonEditState.value.lesson.questionsList) {
                        questionList.add(question)
                    }
                    if (event.questionIndex != -1) {
                        questionList[event.questionIndex] = questionEdit.value.questionEdit
                        lessonEditState.value.lesson.questionsList = questionList
                    } else {
                        questionList.add(questionEdit.value.questionEdit)
                        lessonEditState.value.lesson.questionsList = questionList
                    }

                    var signs: Int = 0
                    var questions: Int = 0

                    for(question in lessonEditState.value.lesson.questionsList) {
                        if (question.questionType == "sign") {
                            signs += 1
                        } else if (question.questionType == "multiple_choice") {
                            questions += 1
                        }
                    }
                    lessonEditState.value.lesson.signs = signs
                    lessonEditState.value.lesson.questions = questions

                    if (event.lessonId == "1"){
                        lessonsDB.document().set(lessonEditState.value.lesson).await()
                    } else {
                        lessonsDB.document(event.lessonId).set(lessonEditState.value.lesson).await()
                    }
                }
             }
        }
    }
}





