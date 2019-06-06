package com.example.todo.data

import java.util.*

data class Task(val id: Int?, val userId: Int, val title: String, val dueDate: Date? = null)