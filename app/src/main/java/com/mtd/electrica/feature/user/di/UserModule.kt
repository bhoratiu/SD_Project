package com.mtd.electrica.feature.user.di

import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.user.data.dataSource.remote.UserAPI
import com.mtd.electrica.feature.user.data.dataSource.remote.WebSocketAPI
import com.mtd.electrica.feature.user.data.repository.DeviceRepositoryImpl
import com.mtd.electrica.feature.user.data.repository.DeviceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideUserAPI(@Named("Service8086") retrofit: Retrofit): UserAPI =
        retrofit.create(UserAPI::class.java)

    @Provides
    fun providesUserRepository(
        userAPI: UserAPI,
        webSocketAPI: WebSocketAPI,
        localStorage: LocalStorage
    ): DeviceRepository =
        DeviceRepositoryImpl(
            userAPI,webSocketAPI, localStorage
        )
}