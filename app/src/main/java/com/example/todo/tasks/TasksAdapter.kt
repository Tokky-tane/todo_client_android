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
    private val items: MutableList<TaskListRow>,
    private val presenter: TasksContract.Presenter
) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    enum class ViewType(val value: Int) { HEADER(0), CONTENT(1) }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rowView = when (viewType) {
            ViewType.CONTENT.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
            }
            ViewType.HEADER.value -> {
                LayoutInflater.from(parent.context).inflate(R.layout.tasks_header, parent, false)
            }
            else ->
                throw RuntimeException()
        }

        return ViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = items[position].content
        when (getItemViewType(position)) {
            ViewType.CONTENT.value -> {
                holder.view.title_view.text = content
            }
            ViewType.HEADER.value -> {
                holder.view.text.text = content
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

interface TaskListRow {
    val viewType: TasksAdapter.ViewType
    val id: Int?
    val content: String
}

class TaskListHeader(date: Date) : TaskListRow {
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

class TaskListContent(private val task: Task) : TaskListRow {

    override val viewType: TasksAdapter.ViewType
        get() = TasksAdapter.ViewType.CONTENT

    override val id: Int?
        get() = task.id

    override val content: String
        get() = task.title
}