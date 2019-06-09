package com.example.todo.addtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.todo.R
import com.example.todo.data.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity(), AddTaskContract.View {

    lateinit var mPresenter: AddTaskContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        deactivateAddTask()

        mPresenter = AddTaskPresenter(this)

        task_title.addTextChangedListener { text ->
            val title = text.toString()
            mPresenter.updateTitle(title)
        }
        add_task_button.setOnClickListener {
            // TODO : set current userId
            mPresenter.addNewTask(Task(null, 1, task_title.text.toString()))
        }

        val spinnerItems = initSpinnerItems()
        spinner.adapter = DueDateSpinnerAdapter(this, spinnerItems)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.unSubscribe()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.subscribe()
    }

    override fun activateAddTask() {
        add_task_button.alpha = 1f
        add_task_button.isEnabled = true
    }

    override fun deactivateAddTask() {
        add_task_button.alpha = 0.38f
        add_task_button.isEnabled = false
    }

    override fun backPreviousView() {
        this.finish()
    }

    override fun showCantAddTask() {
        val snackbar = Snackbar.make(
            add_task_view, R.string.cannot_add_task, Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction("OK") { snackbar.dismiss() }
        snackbar.show()
    }

    private fun initSpinnerItems(): List<DueDateSpinnerItem> {
        val today = Date()
        val calender = Calendar.getInstance()

        calender.time = today
        calender.add(Calendar.DATE, 1)
        val tomorrow = calender.time

        calender.time = today
        calender.add(Calendar.DATE, 7)
        val nextWeek = calender.time

        return listOf(
            DueDateSpinnerItem("today", today),
            DueDateSpinnerItem("tomorrow", tomorrow),
            DueDateSpinnerItem("Next Week", nextWeek),
            DueDateSpinnerItem("Custom", src = R.drawable.ic_chevron_right_black_18dp)
        )
    }
}
