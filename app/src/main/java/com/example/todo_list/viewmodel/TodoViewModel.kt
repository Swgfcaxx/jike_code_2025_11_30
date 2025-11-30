package com.example.todo_list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list.logic.dao.TodoDao
import com.example.todo_list.logic.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class TodoViewModel : ViewModel() {
    // TodoViewModel -> TodoDao -> Todo
    private val todoDao = TodoDao()

    // 私有可变 LiveData，存储 todo 集合（仅 ViewModel 内部可修改）
    private val _todoList = MutableLiveData<MutableList<Todo>>(mutableListOf())

    // 公开不可变 LiveData，供 View 层观察（防止外部修改数据）
    val todoList: LiveData<MutableList<Todo>> = _todoList

    fun addTodo(title: String, description: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            val newTodo = Todo(
                id = UUID.randomUUID().toString(),
                title = title,
                description = description,
                isCompleted = false
            )

            // 通过 todoDao 更新集合
            todoDao.addTodo(newTodo)
            updateTodoList()
        }
    }

    fun deleteTodo(todoId: String){
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodoById(todoId)
            updateTodoList()
        }
    }

    fun toggleTodoStatus(todoId: String){
        viewModelScope.launch(Dispatchers.IO) {
            // 获取当前代办项
            val currentTodo = todoDao.getAllTodos().find { it.id == todoId}
            currentTodo?.let {
                // 切换状态并更新
                val newStatus = !it.isCompleted
                todoDao.updateTodoStatus(todoId,newStatus)
                updateTodoList()
            }
        }
    }


    // 更新 todo 列表
    private fun updateTodoList(){
        viewModelScope.launch(Dispatchers.Main) {
            _todoList.value = todoDao.getAllTodos().toMutableList()
        }
    }
}