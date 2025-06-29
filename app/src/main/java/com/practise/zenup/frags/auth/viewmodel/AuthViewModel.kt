package com.practise.zenup.frags.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practise.zenup.frags.auth.repo.AuthRepo
import com.practise.zenup.frags.auth.repo.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo): ViewModel() {
    private val _authState = MutableLiveData<AuthState>()
    var authState : LiveData<AuthState> = _authState

    fun login(email : String, password : String) {
        viewModelScope.launch {
            authRepo.login(email, password).collectLatest{ _authState.value = it }
        }
    }

    fun signup(email : String, password : String) {
        viewModelScope.launch {
            authRepo.signup(email, password).collectLatest{ _authState.value = it }
        }
    }

}