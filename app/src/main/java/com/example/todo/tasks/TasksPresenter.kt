package com.example.todo.tasks

import com.example.todo.data.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TasksPresenter(private val view: TasksContract.View) : TasksContract.Presenter {

    private val taskService = RetrofitService().getTaskService()
    private val compositeDisposable = CompositeDisposable()

    override fun subscribe() {
        loadTasks()
    }

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

    override fun loadTasks() {
        val disposable = taskService.getTasks(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { tasks ->
                    view.setTasks(tasks)
                },
                onError = {
                    view.showCantLoadTasks()
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun addNewTask() {
        view.showAddNewTask()
    }

    override fun deleteTask(taskId: Int) {
        val disposable = taskService.deleteTask(1, taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showDeletedTask(taskId)
                },
                onError = {
                    view.showCantDeleteTask()
                }
            )

        compositeDisposable.add(disposable)
    }
}