package com.mtd.electrica.feature.signin.di

import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.common.roomDb.AppDatabase
import com.mtd.electrica.feature.admin.data.repository.AdminRepository
import com.mtd.electrica.feature.signin.data.dataSource.local.UserDAO
import com.mtd.electrica.feature.signin.data.dataSource.remote.LoginAPI
import com.mtd.electrica.feature.signin.data.repository.UserRepository
import com.mtd.electrica.feature.signin.data.repository.UserRepositoryImpl
import com.mtd.electrica.feature.signin.domain.useCase.DeleteUserUseCase
import com.mtd.electrica.feature.signin.domain.useCase.DeleteUserUseCaseImpl
import com.mtd.electrica.feature.signin.domain.useCase.FetchAndStoreUsers
import com.mtd.electrica.feature.signin.domain.useCase.FetchAndStoreUsersImpl
import com.mtd.electrica.feature.signin.domain.useCase.GetAllUsersUseCase
import com.mtd.electrica.feature.signin.domain.useCase.GetAllUsersUseCaseImpl
import com.mtd.electrica.feature.signin.domain.useCase.RegisterUseCase
import com.mtd.electrica.feature.signin.domain.useCase.RegisterUseCaseImpl
import com.mtd.electrica.feature.signin.domain.useCase.SignInUseCase
import com.mtd.electrica.feature.signin.domain.useCase.SignInUseCaseImpl
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
    fun provideUserAPI(@Named("Service8085") retrofit: Retrofit): LoginAPI =
        retrofit.create(LoginAPI::class.java)

    @Provides
    fun provideUserDAO(database: AppDatabase): UserDAO =
        database.userDao()

    @Provides
    fun provideUserRepository(
        loginAPI: LoginAPI,
        userDAO: UserDAO,
        localStorage: LocalStorage
    ): UserRepository = UserRepositoryImpl(loginAPI, userDAO, localStorage)

    @Provides
    fun providesRegisterUseCase(
        userRepository: UserRepository
    ): RegisterUseCase = RegisterUseCaseImpl(userRepository)

    @Provides
    fun providesSignInUseCase(
        userRepository: UserRepository
    ): SignInUseCase = SignInUseCaseImpl(userRepository)

    @Provides
    fun providesFetchAndStoreUseCase(
        userRepository: UserRepository
    ): FetchAndStoreUsers = FetchAndStoreUsersImpl(userRepository)

    @Provides
    fun providesGetAllUsersUseCase(
        userRepository: UserRepository
    ): GetAllUsersUseCase = GetAllUsersUseCaseImpl(userRepository)

    @Provides
    fun providesDeleteUseCase(
        userRepository: UserRepository,
        adminRepository: AdminRepository
    ): DeleteUserUseCase = DeleteUserUseCaseImpl(userRepository, adminRepository)

}
