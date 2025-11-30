package com.example.todo_list.logic.dao

import com.example.todo_list.logic.model.Todo

class TodoDao {
    // 内存中存储可变的 todo 集合
    private val todoList = mutableListOf<Todo>()

    // 添加 todo
    fun addTodo(todo :Todo){
        todoList.add(todo)
    }

    // 删除 todo
    fun deleteTodoById(id :String){
        todoList.removeIf{it.id == id}
    }

    // 更新 todo status
    fun updateTodoStatus(id: String, isCompleted: Boolean){
        todoList.find { it.id == id }?.let { todo ->
            todo.isCompleted = isCompleted
        }
    }

    // 获取 todo
    fun getAllTodos(): List<Todo>{
        return todoList.toList() // 返回不可变集合，防止外部直接修改
    }
}