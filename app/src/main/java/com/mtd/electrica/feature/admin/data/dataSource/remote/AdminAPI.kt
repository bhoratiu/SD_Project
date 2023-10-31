package com.mtd.electrica.feature.admin.data.dataSource.remote

import com.mtd.electrica.feature.user.data.repository.Device
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdminAPI {
    @POST("api/v1/device")
    fun addDevice(@Body device: Device): Single<Device>

    @GET("api/v1/device/{id}")
    fun getDeviceById(@Path("id") id: Long): Single<Device>

    @GET("api/v1/device/getAll")
    fun getAllDevices(): Single<List<Device>>

    @PUT("api/v1/device/{id}")
    fun updateDevice(@Path("id") id: Long, @Body device: Device): Single<Device>

    @DELETE("api/v1/device/{id}")
    fun deleteDevice(@Path("id") id: Long): Completable

    @POST("api/v1/device/{deviceId}/add-ids")
    fun addIdsToDevice(@Path("deviceId") deviceId: Long, @Body userIds: List<Long>): Single<Device>

    @POST("api/v1/device/{deviceId}/remove-ids")
    fun removeIdsFromDevice(
        @Path("deviceId") deviceId: Long,
        @Body userIds: List<Long>
    ): Single<Device>

    @GET("api/v1/device/user/{userId}")
    fun getUserDevices(@Path("userId") userId: Long): Single<List<Device>>

    @POST("api/v1/device/unlinkUserDevices")
    fun unlinkUserDevices(@Body userId: Long): Completable
}