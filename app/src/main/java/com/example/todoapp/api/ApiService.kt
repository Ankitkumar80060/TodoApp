package com.example.todoapp.api

import com.example.todoapp.model.ApiData
import com.example.todoapp.model.Data
import com.example.todoapp.util.Util.URL_END_POINT_FOR_CREATE
import com.example.todoapp.util.Util.URL_END_POINT_FOR_GET
import com.example.todoapp.util.Util.URl_END_POINT_FOR_DELETE
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(URL_END_POINT_FOR_GET)
    suspend fun getTodoItem():Response<ApiData>

    @POST(URL_END_POINT_FOR_CREATE)
    suspend fun createTodoTask(@Body body: JsonObject):Response<JsonObject>

    // https://editor.mobillor.net/api/todo?todo_id=3
    @DELETE(URl_END_POINT_FOR_DELETE)
    suspend fun deleteTaskById(@Query("todo_id") todo:Int):Response<JsonObject>

}