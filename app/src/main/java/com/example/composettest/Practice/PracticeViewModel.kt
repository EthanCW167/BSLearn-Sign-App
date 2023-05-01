package com.example.composettest.Practice

import android.provider.Settings
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.composettest.Domain.model.*
import com.example.composettest.LessonMaker.SignDataState
import com.example.composettest.LessonMaker.SignNameListState
import com.example.composettest.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle
): ViewModel() {
    var signDataDB = Firebase.firestore.collection("signData")

    var usersDB = Firebase.firestore.collection("users")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    var _lesson = mutableStateOf(PracticeLessonState())
    val lesson: MutableState<PracticeLessonState> = _lesson

    private val _signNameList = mutableStateOf(SignNameListState())
    val signNameList: State<SignNameListState> = _signNameList

    private val _signDataList = mutableStateOf(SignDataState())
    val signDataList: State<SignDataState> = _signDataList

    private val _searchList = MutableStateFlow(signDataList.value.signDataList)

    var user = User()

    val searchList = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_searchList) { text, signData ->
            if(text.isBlank()) {
                signData
            } else {
                delay(2000L)
                signData.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchList.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        viewModelScope.launch {
            val querySnapshot = signDataDB.get().await()

            var signDataArray: MutableList<FSignData> = emptyList<FSignData>().toMutableList()

            var signNames: MutableList<String> = emptyList<String>().toMutableList()

            for (document in querySnapshot.documents) {

                val signData = document.toObject<FSignData>()
                if (signData != null) {
                    signDataArray.add(signData)
                    signNames.add(signData.sign)
                }
            }
            _signDataList.value = signDataList.value.copy(signDataArray)
            _signNameList.value = signNameList.value.copy(signNames)
            _searchList.value = signDataArray

            savedStateHandle.get<String>("userId")?.let { userId ->

                val userSnapshot = usersDB.document(userId).get().await()
                var snapUser = userSnapshot.toObject<User>()!!

                user = snapUser
                }
            }
        }


    fun addSign(signData: FSignData){

        var lessonToUpdate = _lesson.value.lesson

        var questionList = _lesson.value.lesson.questionsList.toMutableList()

        questionList.add(FQuestion(signData = signData))
        questionList.add(FQuestion(questionType = "multiple_choice",signData = signData))

        lessonToUpdate.questionsList = questionList

        _lesson.value = lesson.value.copy(lessonToUpdate)
        println("here")
        println(lesson.value.lesson.questionsList.size)

        //words.value = _lesson.value.lesson.questionsList.size

    }

    fun nextScreen(questionIndex: Int, navController: NavController, userId: String) {

        if (questionIndex <= lesson.value.lesson.questionsList.size -1) {


            if (lesson.value.lesson.questionsList[questionIndex].questionType == "multiple_choice") {
                navController.navigate(Screen.PracticeMultiChoice.route + "?questionIndex=${questionIndex}")
            } else if (lesson.value.lesson.questionsList[questionIndex].questionType == "sign") {
                navController.navigate(Screen.PracticeSignView.route + "?questionIndex=${questionIndex}")
            }
        }
        else {
            navController.navigate(Screen.PracticeSummaryScreen.route + "?userId=${userId}")
        }
    }

    fun isCorrect(questionIndex: Int, isCorrect: Int){
        if (isCorrect == 1){
            var lessonUpdate = _lesson.value.lesson
            lessonUpdate.questionsList[questionIndex].isCorrect = 1
            _lesson.value = lesson.value.copy(lessonUpdate)
        } else {
            var lessonUpdate = _lesson.value.lesson
            lessonUpdate.questionsList[questionIndex].isCorrect = 0
            _lesson.value = lesson.value.copy(lessonUpdate)
        }

    }

    fun signExists(sign: String){
        if (!user.signsLearned.contains(sign)){
           user.signsLearned.add(sign)
        }
    }

    fun updateUser(userId: String){
        viewModelScope.launch {
            usersDB.document(userId).set(user).await()
        }
    }
}