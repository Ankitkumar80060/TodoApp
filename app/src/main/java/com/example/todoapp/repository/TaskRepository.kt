package com.example.todoapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.api.ApiService
import com.example.todoapp.model.ApiData

class TaskRepository(private var apiService: ApiService) {

    private var todoItemLiveData=MutableLiveData<ApiData>()

    val todoItem:LiveData<ApiData>
        get()=todoItemLiveData

    suspend fun  getTodoData(){
        val result=apiService.getTodoItem()
        if(result.body() !=null){
            todoItemLiveData.postValue(result.body())
        }
    }
}