package com.example.savy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.savy.model.LoginBody
import com.example.savy.model.LoginResponse
import com.example.savy.services.APIService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    fun login(loginBody: LoginBody, callback: (LoginResponse) -> Unit) {
        APIService.UserService.signIn(loginBody).enqueue(
            object : retrofit2.Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    response.body()?.let { callback(it) }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                }

            })
    }
/*
    fun forgotpwd(email: JSONObject){
        APIService.create().forgotpwd(email).enqueue(
            object : retrofit2.Callback<JSONObject>{
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                    response.body()?.let { println("email shih") }
                }
                override fun onFailure(call: Call<JSONObject>, t: Throwable) {

                }
            }
        )
    }*/

}


