package com.example.savy.services

import com.example.savy.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIService {

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
    }
    val UserService: UserServiceAPI = retrofit().create(UserServiceAPI::class.java)

    val ProductService: ProduitServiceAPI by lazy {
        retrofit().create(ProduitServiceAPI::class.java)
    }

}