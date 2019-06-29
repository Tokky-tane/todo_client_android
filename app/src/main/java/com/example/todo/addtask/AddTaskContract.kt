package com.example.todo.addtask

import com.example.todo.BasePresenter
import java.io.Serializable
import java.util.*

interface AddTaskContract {
    interface View {
        fun showCantAddTask()
        fun activateAddTask()
        fun deactivateAddTask()
        fun backPreviousView()
        fun setCustomDate(date: Date)
    }

    interface Presenter : BasePresenter, Serializable {
        fun addNewTask(title: String, dueDate: Date)
        fun updateTitle(title: String)
        fun updateCustomDate(date: Date)
    }
}