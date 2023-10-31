package com.mtd.electrica.feature.signin.domain.useCase

import io.reactivex.rxjava3.core.Completable

interface DeleteUserUseCase {
    fun deleteUser(email: String, userId: Long): Completable
}