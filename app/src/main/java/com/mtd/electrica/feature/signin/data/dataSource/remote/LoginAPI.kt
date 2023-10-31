package com.mtd.electrica.feature.signin.data.dataSource.remote

import com.mtd.electrica.feature.signin.data.model.LoginBody
import com.mtd.electrica.feature.signin.data.model.LoginResponse
import com.mtd.electrica.feature.signin.data.model.RegisterBody
import com.mtd.electrica.feature.signin.data.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface LoginAPI {

    @POST
    fun login(
        @Url url: String,
        @Body loginBody: LoginBody
    ): Single<LoginResponse>

    @POST
    fun register(
        @Url url: String,
        @Body registerBody: RegisterBody
    ): Completable

    @GET
    fun getAllUsers(
        @Url url: String
    ): Single<List<User>>

    @DELETE("api/v1/employee/{email}")
    fun deleteUser(@Path("email") email: String): Completable

    @GET("api/v1/employee/getAll")
    fun getAllUsers(): Single<List<User>>
}