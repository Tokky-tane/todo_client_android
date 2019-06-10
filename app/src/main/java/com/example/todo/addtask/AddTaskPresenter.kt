package com.example.todo.addtask

import com.example.todo.data.RetrofitService
import com.example.todo.data.Task
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddTaskPresenter(private val view: AddTaskContract.View) : AddTaskContract.Presenter {
    private val taskService = RetrofitService().getTaskService()
    private val compositeDisposable = CompositeDisposable()

    override fun subscribe() {

    }

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

    override fun addNewTask(task: Task) {
        // TODO : set current userId
        val disposable = taskService.postTask(1, task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.backPreviousView()
                },
                onError = {
                    view.showCantAddTask()
                }
            )

        compositeDisposable.add(disposable)
    }

    override fun updateTitle(title: String) {
        if (title.isBlank()) {
            view.deactivateAddTask()
        } else {
            view.activateAddTask()
        }
    }

    override fun updateCustomDate(date: Date) {
        view.setCustomDate(date)
    }
}
