package com.mtd.electrica.feature.user.data.repository

import androidx.lifecycle.LiveData

interface UserRepository {
    fun getUserDevices(): LiveData<List<Device>>
}