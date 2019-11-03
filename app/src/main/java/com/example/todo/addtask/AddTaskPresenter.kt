package com.example.todo.addtask

import com.example.todo.data.RetrofitService
import com.example.todo.data.Task
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.*

class AddTaskPresenter(private val view: AddTaskContract.View) : AddTaskContract.Presenter {
    private val taskService = RetrofitService().getTaskService()
    private val compositeDisposable = CompositeDisposable()

    override fun subscribe() {}

    override fun unSubscribe() {
        compositeDisposable.clear()
    }

    override fun addNewTask(title: String, dueDate: Date) {
        fun addNewTask(token: String, newTask: Task) {
            val disposable = taskService.postTask(token, newTask)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        view.backPreviousView()
                    },
                    onError = {
                        throw it
                    }
                )
            compositeDisposable.add(disposable)

        }

        val user = FirebaseAuth.getInstance().currentUser!!
        val newTask = Task(null, user.uid, title, dueDate)
        user.getIdToken(true)
            .addOnCompleteListener { getIdTokenTask ->
                if (getIdTokenTask.isSuccessful) {
                    val token = getIdTokenTask.result!!.token!!
                    addNewTask(token, newTask)
                }
            }
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
