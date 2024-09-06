package com.example.pastimerush.data

import com.example.pastimerush.data.database.MoodEntryDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataBindModule {

    @Binds
    @Singleton
    fun bindMoodRepository(moodRepositoryImpl: MoodRepositoryImpl): MoodRepository

}