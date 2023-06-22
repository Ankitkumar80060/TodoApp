package com.example.todoapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.ApiData
import com.example.todoapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val taskRepository: TaskRepository) :ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.getTodoData()
        }
    }

    val todoItem:LiveData<ApiData>
        get()=taskRepository.todoItem

}