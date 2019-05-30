package com.example.todo.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TasksAdapter(private val  tasks: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    class TaskViewHolder(val taskView: ConstraintLayout) : RecyclerView.ViewHolder(taskView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false) as ConstraintLayout

        return TaskViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskView.title_view.text = tasks[position].title
    }

    override fun getItemCount() = tasks.size
}