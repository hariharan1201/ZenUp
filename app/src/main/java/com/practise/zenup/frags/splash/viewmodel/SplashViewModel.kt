package com.practise.zenup.frags.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practise.zenup.frags.splash.repo.SplashRepo
import com.practise.zenup.frags.splash.repo.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val splashRepo: SplashRepo): ViewModel() {
    private val _slashState = MutableLiveData<SplashState>()
    val splashState = _slashState

     fun getAuthStatus() {
        viewModelScope.launch{
            splashRepo.checkState().collectLatest {
                _slashState.value = it
            }
        }
    }

}