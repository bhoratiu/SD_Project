package com.mtd.electrica.feature.user.data.dataSource.remote

import com.mtd.electrica.feature.user.data.repository.Device
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {

    @GET("/api/v1/device/user/{userId}")
    fun getUserDevices(
        @Path("userId") userId: Long
    ): Single<List<Device>>
}