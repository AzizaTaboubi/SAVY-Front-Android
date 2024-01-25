package com.example.savy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
    val _id: String,
    val fullname: String,
    val profilepic: String,
    val email: String,
    val password: String,
    val numTel: String
): Serializable

data class LoginResponse(
    val token:String,
    val user: User
)

data class UserBody(
    val fullname: String,
    val email: String,
    val password: String,
    val numTel: String
)
data class LoginBody(
    val email: String,
    val password: String
)
data class EmailBody(
    val email: String
)
data class Otp(
    val otp: String
)
data class ChangePwdBody(
    val newPass: String,
    val email: String
)
data class UpdateProfileBody(
    val email: String,
    val fullname: String,
    val numTel: String
)