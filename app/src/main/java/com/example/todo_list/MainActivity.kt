package com.example.todo_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.ui.TodoAdapter
import com.example.todo_list.viewmodel.TodoViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var todoViewModel: TodoViewModel // 待办事项视图模型
    private lateinit var titleEditText: EditText // 标题输入框
    private lateinit var descriptionEditText: EditText // 描述输入框
    private lateinit var addButton: Button // 添加按钮
    private lateinit var recyclerView: RecyclerView // 待办事项列表
    private lateinit var adapter: TodoAdapter // 列表适配器


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化 ViewModel
        todoViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[TodoViewModel::class.java]

        // 初始化 UI 组件
        titleEditText = findViewById(R.id.todo_title_edittext)
        descriptionEditText = findViewById(R.id.todo_description_edittext)
        addButton = findViewById(R.id.add_todo_button)
        recyclerView = findViewById(R.id.todo_recycler_view)

        // 给 RecyclerView 设置布局管理器
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化适配器
        adapter = TodoAdapter(
            todos = emptyList(),
            onDeleteClick = {todoId ->
                todoViewModel.deleteTodo(todoId)
            },
            onStatusChange = {todoId,isCompleted ->
                todoViewModel.toggleTodoStatus(todoId)
            }
        )
        recyclerView.adapter = adapter

        //观察 todo 列表数据变化
        todoViewModel.todoList.observe(this){
            // 更新适配器数据
            adapter.updateTodos(it)
        }


        // 设置 todo 的点击事件
        addButton.setOnClickListener{
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim().takeIf { it.isNotEmpty() }

            // 标题不为空时添加待办事项
            if(title.isNotEmpty()){
                todoViewModel.addTodo(title, description)

                // 清空输入框
                titleEditText.text.clear()
                descriptionEditText.text.clear()
            }
        }

    }
}