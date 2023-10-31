package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.signin.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): SignInUseCase {
    override fun execute(username: String, password: String): Completable =
        userRepository.signIn(username, password)
}