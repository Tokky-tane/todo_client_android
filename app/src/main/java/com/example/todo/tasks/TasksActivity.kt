package com.example.todo.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.addtask.AddTaskActivity
import com.example.todo.data.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksContract.View {
    lateinit var mPresenter: TasksContract.Presenter
    private lateinit var tasksAdapter: RecyclerView.Adapter<*>
    private var tasksList = mutableListOf<Task>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        mPresenter = TasksPresenter(this)

        add_button.setOnClickListener { mPresenter.addNewTask() }

        initTasksView()
    }

    private fun initTasksView() {
        tasksAdapter = TasksAdapter(tasksList, mPresenter)

        tasks_view.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(this@TasksActivity)
        }
        ItemTouchHelper(SwipeToDeleteCallback(tasksAdapter as TasksAdapter)).attachToRecyclerView(tasks_view)
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
        tasksAdapter.notifyDataSetChanged()
    }

    override fun showCantLoadTasks() {
        val clickListener = { _: View -> mPresenter.loadTasks() }
        Snackbar.make(tasks_view, R.string.cannot_load_tasks, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, clickListener)
            .show()
    }

    override fun showDeletedTask() {
        Snackbar.make(tasks_view, R.string.deleted_task, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun showCantDeleteTask() {
        Snackbar.make(tasks_view, R.string.cannot_delete_Task, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showAddNewTask() {
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)
    }
}
