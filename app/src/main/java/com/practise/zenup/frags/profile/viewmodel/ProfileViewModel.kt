package com.practise.zenup.frags.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practise.zenup.frags.profile.repo.ProfileRepo
import com.practise.zenup.frags.profile.repo.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepo: ProfileRepo): ViewModel() {
    private val _profileState = MutableLiveData<ProfileState>()
    val profileState = _profileState

    fun getUser() {
        viewModelScope.launch {
            profileRepo.getUserInfo().collectLatest { _profileState.value = it }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            profileRepo.logOut().collectLatest { _profileState.value = it }
        }
    }
}