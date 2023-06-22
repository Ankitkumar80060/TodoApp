package com.example.todoapp.ui

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.ViewModel.MainViewModel
import com.example.todoapp.ViewModel.MainViewModelFactory
import com.example.todoapp.adapter.MyAdapter
import com.example.todoapp.api.ApiClient
import com.example.todoapp.api.ApiService
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.model.Data
import com.example.todoapp.repository.TaskRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: MyAdapter
    private lateinit var apiService: ApiService
    private var progressDialog: ProgressDialog? = null


    var listData = ArrayList<Data>()

    lateinit var todo_text: EditText
    lateinit var create_btn: Button
    lateinit var delete_btn: Button

    lateinit var todoString: String

    var todoId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.recyclerView.layoutManager = LinearLayoutManager(this)

//****************************** dialog ***********************

        val dialog = Dialog(this@MainActivity)

        binding.createTodo.setOnClickListener {
            dialog.setContentView(R.layout.custome_dialog)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)

            todo_text = dialog.findViewById(R.id.etTodo)
            create_btn = dialog.findViewById(R.id.btnCreate)


            create_btn.setOnClickListener {
                createTask()
                dialog.dismiss()
                onBackPressed()

            }
            dialog.show()

        }

        binding.deleteTodo.setOnClickListener {
            dialog.setContentView(R.layout.delete_dialog)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.setCancelable(false)

            todo_text = dialog.findViewById(R.id.etTodo)
            delete_btn = dialog.findViewById(R.id.btnDelete)


            delete_btn.setOnClickListener {
                deleteTask()
                dialog.dismiss()
                onBackPressed()

            }
            dialog.show()

        }


//****************************** API Call ***********************
        apiService = ApiClient.getInstance().create(ApiService::class.java)
        val taskRepository = TaskRepository(apiService)
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(taskRepository))[MainViewModel::class.java]



        binding.fetchTodo.setOnClickListener {
            mainViewModel.todoItem.observe(this, Observer {
                Log.d("Rinku", it.toString())

                listData = it.data as ArrayList<Data>
                myAdapter = MyAdapter(listData)
                binding.recyclerView.adapter = myAdapter

            })

        }


    }

    private fun deleteTask() {
        lifecycleScope.launch {
            showLoading("Please wait Task is Deleting")
            val value = todo_text.text.toString()
            todoId=Integer.parseInt(value)
            val result = apiService.deleteTaskById(todoId!!)
            if (result.isSuccessful) {
                Log.d("Delete", "DeleteUser Sucess:${result.body()}")
                Toast.makeText(this@MainActivity, "Deletion Task successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("Delete", "DeleteUser Fail:${result.message()}")
                Toast.makeText(this@MainActivity, "Deletion Task  Fail!!!", Toast.LENGTH_SHORT)
                    .show()
            }
            progressDialog?.dismiss()

        }
    }

    private fun createTask() {
        lifecycleScope.launch {
            showLoading("Please wait Task is Creating")
            todoString = todo_text.text.toString()
            val body = JsonObject().apply {
                addProperty("todo", todoString)
            }

            val result = apiService.createTodoTask(body)
            if (result.isSuccessful) {
                Log.d("Create", "CreateUser Sucess:${result.body()}")
                Toast.makeText(this@MainActivity, "Task create successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.d("Create", "CreateUser Fail:${result.message()}")
                Toast.makeText(this@MainActivity, "Task create Fail!!!", Toast.LENGTH_SHORT).show()
            }
            progressDialog?.dismiss()
        }
    }

    private fun showLoading(msg: String) {
        progressDialog = ProgressDialog.show(this, null, msg, true)
    }


}