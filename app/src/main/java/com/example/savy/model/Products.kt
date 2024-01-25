package com.example.savy.model

import java.io.Serializable

data class Products (val _id: String,
                     val userID: String,
                     val nom: String,
                     val prix: Int,
                     val image: String,
                     val promo: Int,
                     val etat: String,
                     val marque: String,
                     val boutique: String,
                     val annee: Int,
                     val description: String,
                     val type: String,
                     val city: String
): Serializable


data class ProductResponse(
    val Produit:Products
)
data class ProduitResponse(
    val Products: List<Products>
)

data class AddProduct(
    val userID: String,
    val nom: String,
    val prix: Int,
    val image: String,
    val promo: Int,
    val etat: String,
    val marque: String,
    val annee: Int,
    val description: String,
    val type: String,
    val city: String,
)
data class UpdateProduct(
    val nom: String,
    val prix: Int,
    val image: String,
    val promo: Int,
    val etat: String,
    val marque: String,
    val annee: Int,
    val description: String,
    val type: String,
    val city: String,
)

data class GetByID(
    val userID: String
)