package com.mtd.electrica.feature.signin.data.repository

import android.annotation.SuppressLint
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.signin.data.dataSource.local.UserDAO
import com.mtd.electrica.feature.signin.data.dataSource.remote.LoginAPI
import com.mtd.electrica.feature.signin.data.model.LoginBody
import com.mtd.electrica.feature.signin.data.model.RegisterBody
import com.mtd.electrica.feature.signin.data.model.User
import com.mtd.electrica.utils.RetrofitConstants.BASE_URL
import com.mtd.electrica.utils.RetrofitConstants.GET_ALL_USERS
import com.mtd.electrica.utils.RetrofitConstants.LOGIN_URL
import com.mtd.electrica.utils.RetrofitConstants.REGISTER_URL
import com.mtd.electrica.utils.RetrofitConstants.USER_BASE_URL
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val loginAPI: LoginAPI,
    private val userDAO: UserDAO,
    private val localStorage: LocalStorage
) : UserRepository {
    override fun signIn(username: String, password: String): Completable =
        loginAPI.login(
            LOGIN_URL,
            LoginBody(username, password)
        )
            .flatMapCompletable { loginResponse ->
                if (loginResponse.status) {
                    localStorage.saveUserId(loginResponse.id)
                    localStorage.saveUserType(loginResponse.type)
                    Completable.complete()
                } else {
                    Completable.error(RuntimeException("Login failed"))
                }
            }
            .subscribeOn(Schedulers.io())


    @SuppressLint("CheckResult")
    override fun register(username: String, password: String): Completable =
        loginAPI.register(
            REGISTER_URL,
            RegisterBody(email = username, password = password)
        )
            .subscribeOn(Schedulers.io())

    override fun fetchAndStoreUsers(): Completable =
        loginAPI.getAllUsers(
            BASE_URL + USER_BASE_URL + GET_ALL_USERS
        ).subscribeOn(Schedulers.io())
            .flatMapCompletable { userList ->
                userDAO.insertAllUsers(userList).subscribeOn(Schedulers.io())
            }
            .subscribeOn(Schedulers.computation())

    override fun deleteUser(email: String): Completable =
        loginAPI.deleteUser(email)
            .subscribeOn(Schedulers.io())

    override fun getAllUsers(): Single<List<User>> =
        loginAPI.getAllUsers().subscribeOn(Schedulers.io())

}