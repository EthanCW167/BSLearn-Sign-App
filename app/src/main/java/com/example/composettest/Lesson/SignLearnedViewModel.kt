package com.example.composettest.Lesson

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composettest.Domain.model.User
import com.example.composettest.UserInterface.UserState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignLearnedViewModel @Inject constructor(

): ViewModel() {

    private val _user = mutableStateOf(UserState())
    val user: MutableState<UserState> = _user

    val userDB = Firebase.firestore.collection("users")

    fun getUser(userId: String){
        viewModelScope.launch {
            val userSnapshot = userDB.document(userId).get().await()
            _user.value  = user.value.copy(user = userSnapshot.toObject<User>()!!)
        }
    }

    fun updateSigns(sign: String){
        var beforeUpdate = user.value.user
        if (!user.value.user.signsLearned.contains(sign)){
            beforeUpdate.signsLearned.add(sign)
            _user.value = user.value.copy(beforeUpdate)
        }
    }

    fun saveUserChanges(userId: String){
        viewModelScope.launch {
            userDB.document(userId).set(user.value.user).await()
        }
    }

}
