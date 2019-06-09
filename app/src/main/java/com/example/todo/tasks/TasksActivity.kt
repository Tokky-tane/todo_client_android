package com.example.todo.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.addtask.AddTaskActivity
import com.example.todo.data.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksContract.View {
    lateinit var mPresenter: TasksContract.Presenter
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var tasksList = ArrayList<Task>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        mPresenter = TasksPresenter(this)

        add_button.setOnClickListener {
            mPresenter.addNewTask()
        }

        val viewManager = LinearLayoutManager(this)
        viewAdapter = TasksAdapter(tasksList)

        tasks_view.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mPresenter.unSubscribe()
    }

    override fun setTasks(tasks: List<Task>) {
        tasksList.clear()
        tasksList.addAll(tasks)
        viewAdapter.notifyDataSetChanged()
    }

    override fun showCantLoadTasks() {
        val clickListener = { _: View -> mPresenter.loadTasks() }
        Snackbar.make(tasks_view, R.string.cannot_load_tasks, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, clickListener)
            .show()
    }

    override fun showAddNewTask() {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)
    }
}
