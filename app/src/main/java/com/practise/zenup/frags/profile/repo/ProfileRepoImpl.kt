package com.practise.zenup.frags.profile.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.practise.zenup.model.FirebaseOps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
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
        firebaseOps.getFirebaseAuth().signOut().apply {
            try {
                firebaseOps.getFirebaseFireStore().clearPersistence().await()
                Log.d("state","CLean")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("state","not Clean")
            }
        }
        emit(ProfileState.LogOut)
    }.flowOn(Dispatchers.IO)
}