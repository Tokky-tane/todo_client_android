package com.example.todo.addtask

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DueDatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var mPresenter: AddTaskContract.Presenter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c.set(year, month, dayOfMonth, 0, 0, 0)
        c.set(Calendar.MILLISECOND, 0)
        mPresenter.updateCustomDate(c.time)
    }
}