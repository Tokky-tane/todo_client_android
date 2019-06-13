package com.example.todo.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Task
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.task_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(
    private val tasks: MutableList<Task>,
    private val presnter: TasksContract.Presenter
) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    class TaskViewHolder(val taskView: MaterialCardView) : RecyclerView.ViewHolder(taskView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false) as MaterialCardView

        return TaskViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.taskView.title_view.text = task.title

        if (task.dueDate == null) return
        holder.taskView.due_date_view.text = formatDueDate(task.dueDate)
    }

    fun deleteItem(position: Int) {
        presnter.deleteTask(tasks[position].id!!)
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun formatDueDate(dueDate: Date): String {

        val todayDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val dueDateDayOfMonth = Calendar.getInstance().apply { time = dueDate }.get(Calendar.DAY_OF_MONTH)

        val df = if (dueDateDayOfMonth == todayDate)
            SimpleDateFormat("HH:MM", Locale.JAPAN)
        else
            SimpleDateFormat("MM/dd\nHH:mm", Locale.JAPAN)

        return df.format(dueDate)
    }

    override fun getItemCount() = tasks.size
}