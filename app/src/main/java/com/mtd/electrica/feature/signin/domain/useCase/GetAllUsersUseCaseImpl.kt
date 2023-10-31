package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.signin.data.model.User
import com.mtd.electrica.feature.signin.data.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetAllUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetAllUsersUseCase {
    override fun getAllUsers(): Single<List<User>> =
        userRepository.getAllUsers()
}