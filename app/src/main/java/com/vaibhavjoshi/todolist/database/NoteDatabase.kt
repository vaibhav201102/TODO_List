package com.vaibhavjoshi.todolist.database

import androidx.room.*

@Database(
    entities = [TodoList::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(DateConverters::class)
abstract class NoteDatabase : RoomDatabase(){

    abstract fun todoListDao() : TodoListDao

}