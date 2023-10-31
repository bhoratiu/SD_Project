package com.mtd.electrica.feature.signin.domain.useCase

import io.reactivex.rxjava3.core.Completable

interface FetchAndStoreUsers {
    fun fetchAndStoreUsers(): Completable
}