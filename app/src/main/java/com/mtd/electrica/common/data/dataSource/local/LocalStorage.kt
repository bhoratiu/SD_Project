package com.mtd.electrica.common.data.dataSource.local

import android.content.SharedPreferences
import javax.inject.Inject

class LocalStorage @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString("USER_ID", userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("USER_ID", null)
    }

    fun saveUserType(userType: String) {
        sharedPreferences.edit().putString("USER_TYPE", userType).apply()
    }

    fun getUserType(): String? {
        return sharedPreferences.getString("USER_TYPE", null)
    }
}
