package com.practise.zenup.frags.auth.repo

import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun login(email: String, password : String) : Flow<AuthState>
    fun signup(email: String, password: String) : Flow<AuthState>
}