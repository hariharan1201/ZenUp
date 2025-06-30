package com.practise.zenup.frags.profile.repo

import com.practise.zenup.model.FirebaseOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileRepoImpl @Inject constructor(private val firebaseOps: FirebaseOps): ProfileRepo {
    override fun getUserInfo(): Flow<ProfileState> = flow<ProfileState> {
        emit(ProfileState.Loading)
        val data = firebaseOps.getFirebaseAuth().currentUser
        if(data != null) emit(ProfileState.Success(data)) else emit(ProfileState.Failed)
    }.flowOn(Dispatchers.IO)

    override fun logOut(): Flow<ProfileState> = flow<ProfileState> {
        emit(ProfileState.Loading)
        firebaseOps.getFirebaseAuth().signOut()
        emit(ProfileState.LogOut)
    }.flowOn(Dispatchers.IO)
}