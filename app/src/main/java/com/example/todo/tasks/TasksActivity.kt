package com.example.todo.tasks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R

class TasksActivity : AppCompatActivity() {
    lateinit var tasksFragment:TasksFragment
    lateinit var tasksPresenter: TasksPresenter

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        tasksFragment = TasksFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.tasks_container, tasksFragment)
            .commit()

        tasksPresenter = TasksPresenter(tasksFragment)
    }
}
