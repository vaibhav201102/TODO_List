package com.vaibhavjoshi.todolist.database

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun addListItem(addNote : TodoList)
    fun getList() : Flow<List<TodoList>>
    suspend fun updateListItem(addNote : TodoList)
    suspend fun deleteListItem(addNote : TodoList)

}