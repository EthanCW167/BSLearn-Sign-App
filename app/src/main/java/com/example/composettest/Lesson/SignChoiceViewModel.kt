package com.example.composettest.Lesson

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.FSignData
import com.example.composettest.Domain.model.User
import com.example.composettest.LessonMaker.SignDataState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignChoiceViewModel @Inject constructor(
): ViewModel(){

    var signDataDB = Firebase.firestore.collection("signData")

    private val _signDataList = mutableStateOf(SignDataState())
    val signDataList: State<SignDataState> = _signDataList

    init {
        viewModelScope.launch {
            val querySnapshot = signDataDB.get().await()

            var signDataArray: MutableList<FSignData> = emptyList<FSignData>().toMutableList()

            for (document in querySnapshot.documents) {

                val signData = document.toObject<FSignData>()
                if (signData != null) {
                    signDataArray.add(signData)
                }
            }
            _signDataList.value = signDataList.value.copy(signDataArray)

        }
    }
}