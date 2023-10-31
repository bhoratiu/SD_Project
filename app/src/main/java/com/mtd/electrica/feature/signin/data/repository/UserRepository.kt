package com.mtd.electrica.feature.signin.data.repository

import com.mtd.electrica.feature.signin.data.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepository{
    fun signIn(
        username: String,
        password: String
    ):Completable

    fun register(
        username: String,
        password: String
    ):Completable

    fun fetchAndStoreUsers(): Completable

    fun deleteUser(email: String): Completable

    fun getAllUsers(): Single<List<User>>
}

