package com.example.todo.addtask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.data.Task
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.util.*

class AddTaskFragment : Fragment() {
    lateinit var presenter: AddTaskPresenter
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAddTaskButtonState(isError = true)

        task_title.addTextChangedListener { text ->
            val title = text.toString()

            if (title.isBlank()) {
                setAddTaskButtonState(isError = true)
                task_title.error = "please enter title"
            } else {
                setAddTaskButtonState(isError = false)
                task_title.error = null
            }
        }

        add_task_button.setOnClickListener {
            // TODO : set current userId
            presenter.addTask(Task(null, 1, task_title.text.toString()))
        }

        val spinnerItems = initSpinnerItems()
        spinner.adapter = DueDateSpinnerAdapter(context!!, spinnerItems)
    }

    private fun setAddTaskButtonState(isError: Boolean) {
        if (isError) {
            add_task_button.alpha = 0.38f
            add_task_button.isEnabled = false
        } else {
            add_task_button.alpha = 1f
            add_task_button.isEnabled = true
        }
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }

    fun backView() {
        val activity = context as AddTaskActivity
        activity.finish()
    }

    fun showCantAddSnackBar() {
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

