package com.example.pastimerush.data

import android.app.Application
import androidx.room.Room
import com.example.pastimerush.data.database.MoodDatabase
import com.example.pastimerush.data.database.MoodEntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoodDatabase {
        return Room.databaseBuilder(app, MoodDatabase::class.java, "mood-database").build()
    }

    @Provides
    @Singleton
    fun provideMoodEntryDao(db: MoodDatabase): MoodEntryDao {
        return db.moodEntryDao()
    }

}