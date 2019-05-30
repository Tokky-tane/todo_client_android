package com.example.todo.tasks

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R

class TasksActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val tasksFragment = TasksFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.tasks_container, tasksFragment)
            .commit()

        val tasksPresenter = TasksPresenter(tasksFragment)
    }
}
