package com.example.todo.addtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.DrawableRes
import com.example.todo.R
import kotlinx.android.synthetic.main.due_date_spinner_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class DueDateSpinnerAdapter(context: Context, private val dueDateItems: List<DueDateSpinnerItem>) :
    ArrayAdapter<DueDateSpinnerItem>(context, R.layout.due_date_spinner_item, dueDateItems) {

    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.due_date_spinner_item, parent, false)
        val item = dueDateItems[position]

        view.title.text = item.title
        if (item.date != null)
            view.date.text = formatDate(item.date)

        if (item.src != null)
            view.imageView.setImageResource(item.src)
        else
            view.imageView.setImageDrawable(null)

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(R.layout.due_date_spinner_item, parent, false)
        val item = dueDateItems[position]

        view.title.text = item.title
        if (item.date != null)
            view.date.text = formatDate(item.date)

        view.imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_18dp)
        return view
    }

    private fun formatDate(date: Date): String {
        val df = SimpleDateFormat("MM/dd E", Locale.JAPAN)

        return df.format(date)
    }
}

data class DueDateSpinnerItem(val title: String, val date: Date? = null, @DrawableRes val src: Int? = null)