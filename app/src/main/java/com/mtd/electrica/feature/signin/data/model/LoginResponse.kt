package com.mtd.electrica.feature.signin.data.model

data class LoginResponse (
    val message: String,
    val status: Boolean,
    val type: String,
    val id: String
)