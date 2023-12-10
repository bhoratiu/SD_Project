package com.mtd.electrica.feature.user.data.dataSource.remote

import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import com.mtd.electrica.feature.user.data.repository.Device
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {

    @GET("/api/v1/device/user/{userId}")
    fun getUserDevices(
        @Path("userId") userId: Long
    ): Single<List<Device>>

    @POST("/api/v1/device/{deviceId}/associateUser")
    fun associateUserWithDevice(
        @Path("deviceId") deviceId: Long,
        @Body userId: Long
    ): Single<Response<Void>>
}