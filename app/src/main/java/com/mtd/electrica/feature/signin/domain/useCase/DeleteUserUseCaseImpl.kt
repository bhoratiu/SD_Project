package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.admin.data.repository.AdminRepository
import com.mtd.electrica.feature.signin.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DeleteUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val adminRepository: AdminRepository
) : DeleteUserUseCase {
    override fun deleteUser(email: String, userId: Long): Completable =
        userRepository
            .deleteUser(email)
            .andThen(adminRepository.unlinkUserDevices(userId))
            .subscribeOn(Schedulers.io())
}