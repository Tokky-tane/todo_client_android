package com.example.todo.tasks


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.todo.R
import com.example.todo.data.Task
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_tasks.*
import kotlinx.android.synthetic.main.task_item.view.*

class TasksFragment : Fragment() {

    lateinit var tasksPresenter: TasksPresenter
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var tasksList = ArrayList<Task>()
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(context)
        viewAdapter = TasksAdapter(tasksList)

        tasks_view.apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }
    }

    override fun onResume() {
        super.onResume()
        tasksPresenter.loadTasks()
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }

    fun setTasks(tasks: List<Task>) {
        tasksList.clear()
        tasksList.addAll(tasks)
        viewAdapter.notifyDataSetChanged()
    }
}