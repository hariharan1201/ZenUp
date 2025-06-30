package com.practise.zenup.frags.profile.repo

import com.google.firebase.auth.FirebaseUser

sealed class ProfileState {
    data object Loading : ProfileState()
    data class Success(val data: FirebaseUser) : ProfileState()
    data object Failed : ProfileState()
    data object LogOut : ProfileState()
}