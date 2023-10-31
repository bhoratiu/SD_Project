package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.signin.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : RegisterUseCase {
    override fun execute(username: String, password: String): Completable =
        userRepository.register(username, password)
}