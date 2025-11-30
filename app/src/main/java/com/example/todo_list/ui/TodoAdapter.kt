package com.example.todo_list.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list.R
import com.example.todo_list.logic.model.Todo

class TodoAdapter (
    private var todos: List<Todo>,
    private val onDeleteClick: (String) -> Unit,
    private val onStatusChange: (String, Boolean) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    // 更新数据源
    fun updateTodos(newTodos: List<Todo>){
        todos = newTodos
        notifyDataSetChanged()
    }

    // 创建 ViewHolder 内部类
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val titleTextView: TextView = itemView.findViewById(R.id.todo_title) // 待办事项标题
        val descriptionTextView: TextView = itemView.findViewById(R.id.todo_description) // 待办事项描述
        val checkBox: CheckBox = itemView.findViewById(R.id.todo_checkbox) // 完成状态复选框
        val deleteButton: Button = itemView.findViewById(R.id.delete_button) // 删除按钮
    }

    // 创建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false)
        return TodoViewHolder(view)
    }

    // 对 ViewHolder 进行数据绑定
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        holder.titleTextView.text = todo.title
        holder.descriptionTextView.text = todo.description ?: ""
        holder.checkBox.isChecked = todo.isCompleted

        // 设置删除按钮点击事件
        holder.deleteButton.setOnClickListener{
            onDeleteClick(todo.id)
        }

        // 设置复选框状态改变事件
        holder.checkBox.setOnCheckedChangeListener{_,isChecked ->
            onStatusChange(todo.id,isChecked)
        }
    }

    override fun getItemCount(): Int = todos.size
}