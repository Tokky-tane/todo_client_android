package com.example.todo.addtask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.view.isVisible
import com.example.todo.R
import kotlinx.android.synthetic.main.due_date_spinner_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class DueDateSpinnerAdapter(context: Context, private val dueDateItems: Array<DueDateSpinnerItem>) :
    BaseAdapter() {

    enum class ViewType { DATE, CUSTOM }

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.due_date_spinner_item, parent, false)
        val item = dueDateItems[position]

        view.title.text = item.title
        view.date.text = item.dateString

        if (item.viewType == ViewType.CUSTOM) {
            view.imageView.setImageResource(R.drawable.ic_chevron_right_black_18dp)

            view.date.isVisible = false
            view.imageView.isVisible = true
        } else {
            view.date.isVisible = true
            view.imageView.isVisible = false
        }

        return view
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(R.layout.due_date_spinner_item, parent, false)
        val item = dueDateItems[position]

        view.title.text = item.title
        view.date.text = item.dateString

        view.imageView.setImageResource(R.drawable.ic_arrow_drop_down_black_18dp)
        return view
    }

    override fun getCount() = dueDateItems.size

    override fun getItem(position: Int) = dueDateItems[position]

    override fun getItemId(position: Int) = position.toLong()
}

abstract class DueDateSpinnerItem {
    abstract val viewType: DueDateSpinnerAdapter.ViewType
    abstract val title: String
    abstract var date: Date
    var dateString: String = ""
        get() {
            val df = SimpleDateFormat("MM/dd E", Locale.JAPAN)
            return df.format(date)
        }
        private set
}

class DueDateSpinnerDate(override val title: String, override var date: Date) : DueDateSpinnerItem() {
    override val viewType: DueDateSpinnerAdapter.ViewType
        get() = DueDateSpinnerAdapter.ViewType.DATE
}