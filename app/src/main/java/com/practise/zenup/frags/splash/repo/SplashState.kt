package com.practise.zenup.frags.splash.repo

sealed class SplashState {
    data object LoggedIn : SplashState()
    data object LogIn : SplashState()
}