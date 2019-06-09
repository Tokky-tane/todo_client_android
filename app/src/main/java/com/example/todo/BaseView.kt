package com.example.todo

interface BaseView<T> {
    fun setPresenter(presenter: T)
}