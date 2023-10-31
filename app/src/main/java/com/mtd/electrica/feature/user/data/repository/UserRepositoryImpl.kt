package com.mtd.electrica.feature.user.data.repository

import androidx.lifecycle.LiveData
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.signin.data.model.User
import com.mtd.electrica.feature.user.data.dataSource.remote.UserAPI
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val userAPI: UserAPI,
    private val localStorage: LocalStorage
) {
    fun getUserDevices(userId: Long): Single<List<Device>> {
        return userAPI.getUserDevices(userId)
            .subscribeOn(Schedulers.io())
    }
}