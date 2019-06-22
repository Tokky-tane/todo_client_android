package com.example.todo.addtask

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.todo.R
import com.example.todo.data.Task
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

class AddTaskActivity : AppCompatActivity(), AddTaskContract.View {

    lateinit var mPresenter: AddTaskContract.Presenter
    private lateinit var dueDateSpinnerItems: Array<DueDateSpinnerItem>
    private lateinit var dueDateSpinnerAdapter: DueDateSpinnerAdapter

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
            val title = task_title.text.toString()
            val dueDate = (spinner.selectedItem as DueDateSpinnerItem).date
            mPresenter.addNewTask(Task(null, 1, title, dueDate))
        }

        dueDateSpinnerItems = initSpinnerItems()
        dueDateSpinnerAdapter = DueDateSpinnerAdapter(this, dueDateSpinnerItems)
        spinner.adapter = dueDateSpinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (dueDateSpinnerItems[position].viewType == DueDateSpinnerAdapter.ViewType.CUSTOM)
                    showDueDatePicker()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
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

    private fun initSpinnerItems(): Array<DueDateSpinnerItem> {
        var today = Date()
        val calender = Calendar.getInstance()
        calender.time = today

        calender.set(Calendar.HOUR_OF_DAY, 0)
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 0)
        calender.set(Calendar.MILLISECOND, 0)

        today = calender.time

        calender.add(Calendar.DATE, 1)
        val tomorrow = calender.time

        calender.time = today
        calender.add(Calendar.DATE, 7)
        val nextWeek = calender.time

        return arrayOf(
            DueDateSpinnerDate("today", today),
            DueDateSpinnerDate("tomorrow", tomorrow),
            DueDateSpinnerDate("Next Week", nextWeek),
            object : DueDateSpinnerItem() {
                override val viewType: DueDateSpinnerAdapter.ViewType = DueDateSpinnerAdapter.ViewType.CUSTOM
                override val title: String = "Custom"
                override var date: Date = Date()
            }
        )
    }

    fun showDueDatePicker() {
        val pickerFragment = DueDatePickerFragment()

        pickerFragment.mPresenter = mPresenter
        pickerFragment.show(this.supportFragmentManager, "timepicker")
    }

    override fun setCustomDate(date: Date) {
        dueDateSpinnerItems[3].date = date
        dueDateSpinnerAdapter.notifyDataSetChanged()
    }
}
