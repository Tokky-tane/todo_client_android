package com.example.todo.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.Task
import kotlinx.android.synthetic.main.task_item.view.*
import kotlinx.android.synthetic.main.tasks_header.view.*
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(
    private val items: MutableList<TasksRow>,
    private val presenter: TasksContract.Presenter
) :
    RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    enum class ViewType(val value: Int) { HEADER(0), CONTENT(1) }

    class TaskViewHolder(val taskView: View) : RecyclerView.ViewHolder(taskView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val itemView = when (viewType) {
            ViewType.CONTENT.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
            }
            ViewType.HEADER.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.tasks_header, parent, false)
            }
            else ->
                throw RuntimeException()
        }

        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val content = items[position].content
        when (getItemViewType(position)) {
            ViewType.CONTENT.value -> {
                holder.taskView.title_view.text = content
            }
            ViewType.HEADER.value -> {
                holder.taskView.text.text = content
            }
        }
    }

    override fun getItemCount() = items.size
    override fun getItemViewType(position: Int) = items[position].viewType.value

    fun deleteItem(position: Int) {
        if (items[position].viewType != ViewType.CONTENT) return

        presenter.deleteTask(items[position].id!!)
    }
}

interface TasksRow {
    val viewType: TasksAdapter.ViewType
    val id: Int?
    val content: String
}

class TasksHeader(date: Date) : TasksRow {
    private var mContent: String

    init {
        val sdf = SimpleDateFormat("MM/dd E", Locale.JAPAN)
        mContent = sdf.format(date)
    }

    override val viewType: TasksAdapter.ViewType
        get() = TasksAdapter.ViewType.HEADER

    override val id: Int?
        get() = null

    override val content: String
        get() = mContent

}

class TasksItem(private val task: Task) : TasksRow {

    override val viewType: TasksAdapter.ViewType
        get() = TasksAdapter.ViewType.CONTENT

    override val id: Int?
        get() = task.id

    override val content: String
        get() = task.title
}