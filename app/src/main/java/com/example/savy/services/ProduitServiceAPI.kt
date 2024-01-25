package com.example.savy.services

import com.example.savy.model.*
import retrofit2.Call
import retrofit2.http.*

interface ProduitServiceAPI {
    //
    @GET("/produit/getByUserID/{id}")
    fun getAll(@Path("id") userID: String): Call<ProduitResponse>
    @GET("/produit/searchUserProd")
    fun getSearch(): Call<ProduitResponse>
    @GET("/produit/produits")
    fun getAll(): Call<ProduitResponse>
    @POST("/produit/addNewProduct")
    fun addProduct(@Body newProd: AddProduct): Call<ProductResponse>
    @POST("/produit/update_prod")
    fun update(updateProduct: UpdateProduct): Call<UpdateProduct>
    //change method Delete
    @POST("/produit/delete_prod")
    fun delete()
    @GET("/produit/scrap_prod")
    fun getScrappedData(): Call<List<ScrappedProduct>>
    @GET("/produit/searchUserProd")
    fun searchProducts(
        @Query("type") type: String,
        @Query("marque") marque: String,
        @Query("prix") prix: Int,
        @Query("annee") annee: Int? = null,
        @Query("city") city: String? = null,
    ): Call<List<Products>>
    @GET("/produit/type")
    fun getProductsByType(@Query("type") type: String): Call<List<Products>>

}