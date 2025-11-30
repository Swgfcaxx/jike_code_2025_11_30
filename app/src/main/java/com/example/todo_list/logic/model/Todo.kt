package com.example.todo_list.logic.model

import java.util.UUID

data class Todo(
    // 唯一标识（用UUID或自增ID，避免重复）
    val id: String = UUID.randomUUID().toString(),
    // 待办标题（必填）
    val title: String,
    // 待办描述（可选，默认null）
    val description: String? = null,
    // 完成状态（默认未完成）
    var isCompleted: Boolean = false
)