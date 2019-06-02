package com.example.todo.addtask

import android.util.Log
import com.example.todo.data.RetrofitService
import com.example.todo.data.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class AddTaskPresenter(private val view: AddTaskFragment) {
    private val taskService = RetrofitService().getTaskService()

    init {
        view.presenter = this
    }

    fun addTask(task: Task) {
        // TODO : set current userId
        val disposable = taskService.postTask(1, task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.backView()
                },
                onError = {
                    view.showCantAddSnackBar()
                }
            )

        view.compositeDisposable.add(disposable)
    }
}
