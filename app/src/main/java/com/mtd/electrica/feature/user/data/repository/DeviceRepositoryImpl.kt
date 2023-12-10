package com.mtd.electrica.feature.user.data.repository

import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.user.data.dataSource.remote.UserAPI
import com.mtd.electrica.feature.user.data.dataSource.remote.WebSocketAPI
import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val userAPI: UserAPI,
    private val webSocketAPI: WebSocketAPI,
    private val localStorage: LocalStorage
): DeviceRepository {
    override fun getUserDevices(userId: Long): Single<List<Device>> {
        return userAPI.getUserDevices(userId)
            .subscribeOn(Schedulers.io())
    }

    override fun associateDeviceWithUser(deviceId: Long, userId: Long): Single<Response<Void>> {
        return userAPI.associateUserWithDevice(deviceId, userId)
            .subscribeOn(Schedulers.io())
    }

    override fun getEnergyConsumptionData(deviceId: Long, date: String): Single<List<EnergyConsumptionData>> {
        return webSocketAPI.getEnergyConsumptionByDeviceAndDate(deviceId, date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}