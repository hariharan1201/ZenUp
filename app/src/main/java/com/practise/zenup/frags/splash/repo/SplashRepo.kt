package com.practise.zenup.frags.splash.repo

import kotlinx.coroutines.flow.Flow

interface SplashRepo {
    fun checkState() : Flow<SplashState>
}