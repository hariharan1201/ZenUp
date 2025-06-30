package com.practise.zenup.frags.auth.repo

import android.util.Log
import com.practise.zenup.model.FirebaseOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepoImpl @Inject constructor(private val firebaseOps: FirebaseOps): AuthRepo {
    override fun login(email: String, password: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        val ops = suspendCoroutine { out ->
            firebaseOps.getFirebaseAuth()
                .signInWithEmailAndPassword(email, password).addOnCompleteListener {
                state -> out.resume(state.isSuccessful)
            }.addOnFailureListener { state-> Log.d("FIREBASE", "${state.message}") }
        }
        if(ops) emit(AuthState.Success) else emit(AuthState.Failed)
    }.flowOn(Dispatchers.IO)

    override fun signup(email: String, password: String): Flow<AuthState> = flow {
        emit(AuthState.Loading)
        val ops = suspendCoroutine { out ->
            firebaseOps.getFirebaseAuth()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    state -> out.resume(state.isSuccessful)
            }
        }
        if(ops) emit(AuthState.Success) else emit(AuthState.Failed)
    }.flowOn(Dispatchers.IO)
}