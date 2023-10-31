package com.mtd.electrica.feature.signin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("employeeid") val employeeId: String,
    @SerializedName("employeename") val employeeName: String,
    val email: String,
    val password: String,
    val type: String
) {
    companion object {
        fun createUser(
            employeeId: String,
            employeeName: String,
            email: String,
            password: String,
            type: String,
        ) = User(
            employeeId,
            employeeName,
            email,
            password,
            type
        )
    }
}