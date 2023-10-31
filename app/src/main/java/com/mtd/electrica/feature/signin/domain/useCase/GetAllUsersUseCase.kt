package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.signin.data.model.User
import io.reactivex.rxjava3.core.Single

interface GetAllUsersUseCase {
    fun getAllUsers(): Single<List<User>>
}