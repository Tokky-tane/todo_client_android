package com.example.todo.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TaskService {
    @GET("users/{user_id}/tasks")
    fun getTasks(@Path("user_id") userId: Int): Single<List<Task>>

    @Headers("Content_type: application/json")
    @POST("users/{user_id}/tasks")
    fun postTask(@Path("user_id") userId: Int, @Body task: Task): Single<Unit>
}

class RetrofitService {
    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    fun getTaskService(): TaskService {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(TaskService::class.java)
    }
}