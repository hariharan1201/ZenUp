package com.practise.zenup.di

import com.practise.zenup.frags.auth.repo.AuthRepo
import com.practise.zenup.frags.auth.repo.AuthRepoImpl
import com.practise.zenup.frags.profile.repo.ProfileRepo
import com.practise.zenup.frags.profile.repo.ProfileRepoImpl
import com.practise.zenup.frags.splash.repo.SplashRepo
import com.practise.zenup.frags.splash.repo.SplashRepoImpl
import com.practise.zenup.frags.todo.repo.ToDoRepo
import com.practise.zenup.frags.todo.repo.ToDoRepoImpl
import com.practise.zenup.model.FirebaseOps
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ZenUpDI {

    @Singleton @Provides
    fun provideFireBaseOps() : FirebaseOps = FirebaseOps()

    @Singleton @Provides
    fun provideAuthRepo() : AuthRepo = AuthRepoImpl(provideFireBaseOps())

    @Singleton @Provides
    fun provideSplashRepo() : SplashRepo = SplashRepoImpl(provideFireBaseOps())

    @Singleton @Provides
    fun provideProfileRepo() : ProfileRepo = ProfileRepoImpl(provideFireBaseOps())

    @Singleton @Provides
    fun provideToDoRepo() : ToDoRepo = ToDoRepoImpl(provideFireBaseOps())

}