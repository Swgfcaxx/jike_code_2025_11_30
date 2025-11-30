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

// 密封类表示列表项类型：分组标题或待办事项
sealed class ListItem {
    data class GroupHeader(val title: String) : ListItem()
    data class TodoItem(val todo: Todo) : ListItem()
}

class TodoAdapter (
    private var items: List<ListItem>,
    private val onDeleteClick: (String) -> Unit,
    private val onStatusChange: (String, Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 更新数据源
    fun updateItems(newItems: List<ListItem>){
        items = newItems
        notifyDataSetChanged()
    }

    // 创建分组标题 ViewHolder
    inner class GroupHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headerTextView: TextView = itemView.findViewById(R.id.group_header_title)
    }

    // 创建待办事项 ViewHolder
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.todo_title) // 待办事项标题
        val descriptionTextView: TextView = itemView.findViewById(R.id.todo_description) // 待办事项描述
        val checkBox: CheckBox = itemView.findViewById(R.id.todo_checkbox) // 完成状态复选框
        val deleteButton: Button = itemView.findViewById(R.id.delete_button) // 删除按钮
    }

    // 返回项类型：0 表示分组标题，1 表示待办事项
    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.GroupHeader -> 0
            is ListItem.TodoItem -> 1
        }
    }

    // 创建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_header, parent, false)
                GroupHeaderViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
                TodoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    // 对 ViewHolder 进行数据绑定
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.GroupHeader -> {
                (holder as GroupHeaderViewHolder).headerTextView.text = item.title
            }
            is ListItem.TodoItem -> {
                val todo = item.todo
                val todoHolder = holder as TodoViewHolder
                
                todoHolder.titleTextView.text = todo.title
                todoHolder.descriptionTextView.text = todo.description ?: ""
                todoHolder.checkBox.isChecked = todo.isCompleted

                // 设置删除按钮点击事件
                todoHolder.deleteButton.setOnClickListener {
                    onDeleteClick(todo.id)
                }

                // 设置复选框状态改变事件
                todoHolder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    onStatusChange(todo.id, isChecked)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}