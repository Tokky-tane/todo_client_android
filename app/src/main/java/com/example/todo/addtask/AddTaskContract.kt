package com.example.todo.addtask

import com.example.todo.BasePresenter
import com.example.todo.data.Task

interface AddTaskContract {
    interface View {
        fun showCantAddTask()
        fun activateAddTask()
        fun deactivateAddTask()
        fun backPreviousView()
    }

    interface Presenter : BasePresenter {
        fun addNewTask(task: Task)
        fun updateTitle(title: String)
    }
}