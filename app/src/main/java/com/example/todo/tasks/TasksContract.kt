package com.example.todo.tasks

import com.example.todo.BasePresenter
import com.example.todo.data.Task

interface TasksContract {
    interface View {
        fun setTasks(tasks: List<Task>)
        fun showCantLoadTasks()
        fun showAddNewTask()
    }

    interface Presenter : BasePresenter {
        fun loadTasks()
        fun addNewTask()
        fun deleteTask(id: Int)
    }
}