package com.mtd.electrica.feature.signin.domain.useCase

import io.reactivex.rxjava3.core.Completable

interface RegisterUseCase {
    fun execute(username: String, password: String): Completable
}