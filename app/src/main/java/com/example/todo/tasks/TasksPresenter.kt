package com.example.todo.tasks

import android.util.Log
import com.example.todo.data.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TasksPresenter(private val view: TasksFragment) {
    private val taskService = RetrofitService().getTaskService()

    init {
        view.presenter = this
    }

    fun loadTasks() {
        val disposable = taskService.getTasks(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { tasks ->
                    view.setTasks(tasks)
                },
                onError = {
                    Log.i("yo",it.toString())
                    view.showCantLoadSnackBar()
                }
            )

        view.compositeDisposable.add(disposable)
    }
}