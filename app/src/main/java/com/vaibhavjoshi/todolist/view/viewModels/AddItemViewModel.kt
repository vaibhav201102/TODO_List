package com.vaibhavjoshi.todolist.view.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhavjoshi.todolist.database.TodoList
import com.vaibhavjoshi.todolist.database.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(private val todoRepository: TodoRepository) : ViewModel(){

    fun getList() : Flow<List<TodoList>> {
        return todoRepository.getList()
    }

    fun addListItem(item : TodoList) {
        viewModelScope.launch {
            todoRepository.addListItem(item)
        }
    }

    fun updateListItem(item : TodoList) {
        viewModelScope.launch {
            todoRepository.updateListItem(item)
        }
    }

    fun deleteListItem(item : TodoList) {
        viewModelScope.launch {
            todoRepository.deleteListItem(item)
        }
    }

}