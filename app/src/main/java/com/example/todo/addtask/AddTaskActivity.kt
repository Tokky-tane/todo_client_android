package com.example.todo.addtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R

class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val addTaskFragment = AddTaskFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.tasks_container, addTaskFragment)
            .commit()

        AddTaskPresenter(addTaskFragment)
    }
}
