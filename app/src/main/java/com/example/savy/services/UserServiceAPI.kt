package com.example.savy.services

import com.example.savy.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServiceAPI {
    @POST("/user/register")
    fun signUp(@Body userBody: UserBody): Call<LoginResponse>

    @POST("/user/send-confirmation-email")
    fun sendVerification(@Body emailBody: EmailBody) : Call<LoginResponse>

    @POST("/user/login")
    fun signIn(@Body loginBody: LoginBody): Call<LoginResponse>

    @POST("/user/forgotPassword")
    fun sendOtp(@Body emailBody: EmailBody): Call<LoginResponse>

    @POST("/user/confirmationOtp")
    fun confirmOtp(@Body otp: Otp): Call<LoginResponse>

    @POST("/user/resetPassword")
    fun changePwd(@Body changePwdBody: ChangePwdBody): Call<LoginResponse>

    @POST("/user/updateProfile")
    fun updateProfile(@Body updateProfileBody: UpdateProfileBody) : Call<LoginResponse>
}