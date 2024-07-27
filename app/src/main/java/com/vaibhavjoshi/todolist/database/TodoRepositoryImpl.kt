package com.vaibhavjoshi.todolist.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(private val todoListDao: TodoListDao) :TodoRepository {

    @WorkerThread
    override suspend fun addListItem(addNote: TodoList) {
        todoListDao.addListItem(addNote)
    }

    override fun getList(): Flow<List<TodoList>> {
        return todoListDao.getList()
    }

    @WorkerThread
    override suspend fun updateListItem(addNote: TodoList) {
        todoListDao.updateListItem(addNote)
    }

    @WorkerThread
    override suspend fun deleteListItem(addNote: TodoList) {
        todoListDao.deleteListItem(addNote)
    }
}