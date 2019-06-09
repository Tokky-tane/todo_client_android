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
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity() {
    lateinit var presenter: TasksPresenter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var tasksList = ArrayList<Task>()
    val compositeDisposable = CompositeDisposable()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        presenter = TasksPresenter(this)
        add_button.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = TasksAdapter(tasksList)

        tasks_view.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTasks()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun setTasks(tasks: List<Task>) {
        tasksList.clear()
        tasksList.addAll(tasks)
        viewAdapter.notifyDataSetChanged()
    }

    fun showCantLoadSnackBar() {
        val clickListener = { _: View -> presenter.loadTasks() }
        Snackbar.make(tasks_view, R.string.cannot_load_tasks, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry, clickListener)
            .show()
    }
}
