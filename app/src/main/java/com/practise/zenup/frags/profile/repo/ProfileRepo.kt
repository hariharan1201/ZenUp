package com.practise.zenup.frags.profile.repo

import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    fun getUserInfo() : Flow<ProfileState>
    fun logOut() : Flow<ProfileState>
}