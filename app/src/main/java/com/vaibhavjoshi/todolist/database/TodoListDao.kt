package com.vaibhavjoshi.todolist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListItem(addNote : TodoList)

    @Query("SELECT * FROM TodoList ORDER BY dateAddedItem DESC")
    fun getList() : Flow<List<TodoList>>

    @Update
    suspend fun updateListItem(addNote : TodoList)

    @Delete
    suspend fun deleteListItem(addNote : TodoList)
}