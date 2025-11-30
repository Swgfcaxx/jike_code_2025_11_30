package com.example.todo_list.logic.dao

import android.content.Context
import com.example.todo_list.logic.model.Todo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class TodoDao (private val context: Context){

    private val TODO_FILE_NAME = "todos.json"

    // 内存中存储可变的 todo 集合
    private val todoList = mutableListOf<Todo>()

    // 初始化时从文件加载数据
    init{
        loadTodosFromFile()
    }

    // 添加 todo
    fun addTodo(todo :Todo){
        todoList.add(todo)
        saveTodosToFile()
    }

    // 删除 todo
    fun deleteTodoById(id :String){
        todoList.removeIf{it.id == id}
        saveTodosToFile()
    }

    // 更新 todo status
    fun updateTodoStatus(id: String, isCompleted: Boolean){
        todoList.find { it.id == id }?.let { todo ->
            todo.isCompleted = isCompleted
            saveTodosToFile()
        }
    }

    // 获取 todo
    fun getAllTodos(): List<Todo>{
        return todoList.toList() // 返回不可变集合，防止外部直接修改
    }

    private fun loadTodosFromFile(){
        try {
            val file = File(context.filesDir, TODO_FILE_NAME)
            if (file.exists()) {
                val jsonString = file.readText()
                val todos = Json.decodeFromString<List<Todo>>(jsonString)
                todoList.clear()
                todoList.addAll(todos)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveTodosToFile() {
        try {
            val file = File(context.filesDir, TODO_FILE_NAME)
            val jsonString = Json.encodeToString(todoList)
            file.writeText(jsonString)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}