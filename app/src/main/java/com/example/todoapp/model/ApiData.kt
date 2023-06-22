package com.example.todoapp.model

data class ApiData(
    val data: List<Data>,
    val msg: String,
    val status: Boolean,
    val statusCode: Int
)