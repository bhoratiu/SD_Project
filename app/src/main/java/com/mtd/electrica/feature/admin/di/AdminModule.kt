package com.mtd.electrica.feature.admin.di

import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.admin.data.dataSource.remote.AdminAPI
import com.mtd.electrica.feature.admin.data.repository.AdminRepository
import com.mtd.electrica.feature.signin.data.dataSource.local.UserDAO
import com.mtd.electrica.feature.signin.data.dataSource.remote.LoginAPI
import com.mtd.electrica.feature.signin.data.repository.UserRepository
import com.mtd.electrica.feature.signin.data.repository.UserRepositoryImpl
import com.mtd.electrica.feature.user.data.dataSource.remote.UserAPI
import com.mtd.electrica.feature.user.data.dataSource.remote.WebSocketAPI
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
    fun provideAdminRepository(
       adminAPI: AdminAPI
    ): AdminRepository = AdminRepository(adminAPI)

    @Provides
    fun provideUserAPI(@Named("Service8086") retrofit: Retrofit): AdminAPI =
        retrofit.create(AdminAPI::class.java)

    @Provides
    fun provideWebSocketAPI(@Named("Service8087") retrofit: Retrofit): WebSocketAPI =
        retrofit.create(WebSocketAPI::class.java)
}