package com.mtd.electrica.feature.signin.domain.useCase

import com.mtd.electrica.feature.signin.data.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class FetchAndStoreUsersImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchAndStoreUsers {
    override fun fetchAndStoreUsers(): Completable =
        userRepository.fetchAndStoreUsers()
}