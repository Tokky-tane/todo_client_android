package com.example.todo.tasks

import com.example.todo.data.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TasksPresenter(private val tasksFragment: TasksFragment) {
    private val taskService = RetrofitService().getTaskService()

    init {
        tasksFragment.presenter = this
    }

    fun loadTasks() {
        val disposable = taskService.getTasks(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tasks ->
                    tasksFragment.setTasks(tasks)
                },
                {
                    tasksFragment.showCantLoadSnackBar()
                }
            )

        tasksFragment.compositeDisposable.add(disposable)
    }
}