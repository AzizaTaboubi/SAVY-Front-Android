package com.example.savy.model

data class ScrappedProduct(
    val id: Int,
    val url: String?,
    val image: String?,
    val title: String,
    val price: String?,
    val promo: String?
)
