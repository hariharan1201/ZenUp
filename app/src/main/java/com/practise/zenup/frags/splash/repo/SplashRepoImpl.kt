package com.practise.zenup.frags.splash.repo

import com.practise.zenup.model.FirebaseOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SplashRepoImpl @Inject constructor(private val firebaseOps: FirebaseOps): SplashRepo {
    override fun checkState(): Flow<SplashState> = flow {
        val data = firebaseOps.getFirebaseAuth().currentUser
        if(data == null){ emit(SplashState.LogIn) }else{ emit(SplashState.LoggedIn) }
    }.flowOn(Dispatchers.IO)
}