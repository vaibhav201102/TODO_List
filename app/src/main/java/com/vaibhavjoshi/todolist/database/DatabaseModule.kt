package com.vaibhavjoshi.todolist.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent :: class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: NoteDatabase): TodoListDao {
        return appDatabase.todoListDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            "Todolist_database"
        ).fallbackToDestructiveMigration().build()
    }
}