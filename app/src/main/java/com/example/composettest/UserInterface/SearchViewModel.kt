package com.example.composettest.UserInterface

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FSignData
import com.example.composettest.Domain.model.Person
import com.example.composettest.LessonMaker.SignDataState
import com.example.composettest.LessonMaker.SignNameListState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(

): ViewModel() {

    var signDataDB = Firebase.firestore.collection("signData")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _signNameList = mutableStateOf(SignNameListState())
    val signNameList: State<SignNameListState> = _signNameList

    private val _signDataList = mutableStateOf(SignDataState())
    val signDataList: State<SignDataState> = _signDataList

    private val _searchList = MutableStateFlow(signDataList.value.signDataList)
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
            _searchList.value = signDataArray
        }
    }
}

private val allPersons = listOf(
    Person(
        firstName = "Philipp",
        lastName = "Lackner"
    ),
    Person(
        firstName = "Beff",
        lastName = "Jezos"
    ),
    Person(
        firstName = "Chris P.",
        lastName = "Bacon"
    ),
    Person(
        firstName = "Jeve",
        lastName = "Stops"
    ),
)

