package com.example.todo.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TaskService {
    @GET("users/{user_id}/tasks")
    fun getTasks(@Header("Authorization") token: String, @Path("user_id") userId: String): Single<List<Task>>

    @Headers("Content_type: application/json")
    @POST("users/{user_id}/tasks")
    fun postTask(@Header("Authorization") token: String, @Path("user_id") userId: String, @Body task: Task): Single<Unit>

    @DELETE("users/{user_id}/tasks/{task_id}")
    fun deleteTask(@Header("Authorization") token: String, @Path("user_id") userId: String, @Path("task_id") taskId: Int): Single<Response<Unit>>
}

class RetrofitService {
    private val gson = GsonBuilder()
        .serializeNulls()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()

    fun getTaskService(): TaskService {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://young-thicket-29507.herokuapp.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(TaskService::class.java)
    }
}