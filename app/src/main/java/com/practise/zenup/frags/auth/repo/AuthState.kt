package com.practise.zenup.frags.auth.repo

sealed class AuthState {
    data object Loading : AuthState()
    data object Success : AuthState()
    data object Failed : AuthState()
}