package com.example.todo.addtask

import android.content.Context
import android.util.AttributeSet
import  androidx.appcompat.widget.AppCompatSpinner

class DueDateSpinner : AppCompatSpinner {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun setSelection(position: Int, animate: Boolean) {
        super.setSelection(position, animate)
        if (position == selectedItemPosition) {
            onItemSelectedListener?.onItemSelected(this, selectedView, position, position.toLong())
        }
    }

    override fun setSelection(position: Int) {
        super.setSelection(position)
        if (position == selectedItemPosition) {
            onItemSelectedListener?.onItemSelected(this, selectedView, position, position.toLong())
        }
    }
}