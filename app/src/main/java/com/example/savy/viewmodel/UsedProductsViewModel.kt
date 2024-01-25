package com.example.savy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.savy.model.Products
import com.example.savy.model.ProduitResponse
import com.example.savy.model.ScrappedProduct
import com.example.savy.services.APIService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class UsedProductsViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<ProductsState> =
        MutableStateFlow(ProductsState.Success(emptyList()))

    val uiState: StateFlow<ProductsState> = _uiState

    fun search(
        type: String?,
        marque: String?,
        prix: Int,
        annee: Int?,
        city: String?,
    ) {
        if (type == null || marque == null) {
            searchAll()
        } else {
            searchSpecific(type, marque, prix, annee, city)
        }

    }

    private fun searchAll() {
        APIService.ProductService.getAll().enqueue(object : Callback<ProduitResponse> {
            override fun onResponse(
                call: Call<ProduitResponse>,
                response: Response<ProduitResponse>
            ) {
                response.body()?.let {
                    _uiState.value = ProductsState.Success(it.Products)
                }
            }

            override fun onFailure(call: Call<ProduitResponse>, t: Throwable) {
                _uiState.value = ProductsState.Error(t)
            }

        })
    }

    private fun searchSpecific(
        type: String,
        marque: String,
        prix: Int,
        annee: Int?,
        city: String?,
    ) {
        APIService.ProductService.searchProducts(type, marque, prix, annee, city).enqueue(
            object : retrofit2.Callback<List<Products>> {
                override fun onResponse(
                    call: Call<List<Products>>,
                    response: Response<List<Products>>
                ) {
                    response.body()?.let {
                        _uiState.value = ProductsState.Success(it)
                    }
                }

                override fun onFailure(call: Call<List<Products>>, t: Throwable) {
                    _uiState.value = ProductsState.Error(t)
                }

            })
    }

    fun filterByType(type: String) {
        APIService.ProductService.getProductsByType(type).enqueue(
            object : retrofit2.Callback<List<Products>> {
                override fun onResponse(
                    call: Call<List<Products>>,
                    response: Response<List<Products>>
                ) {
                    response.body()?.let {
                        _uiState.value = ProductsState.Success(it)
                    }
                }

                override fun onFailure(call: Call<List<Products>>, t: Throwable) {
                    _uiState.value = ProductsState.Error(t)
                }

            })
    }

    sealed class ProductsState {
        data class Success(val products: List<Products>) : ProductsState()
        data class Error(val exception: Throwable) : ProductsState()
    }
}
