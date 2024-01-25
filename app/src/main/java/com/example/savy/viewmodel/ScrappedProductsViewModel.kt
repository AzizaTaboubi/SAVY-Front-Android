package com.example.savy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.savy.model.ScrappedProduct
import com.example.savy.services.APIService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ScrappedProductsViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<ProductsState> = MutableStateFlow(ProductsState.Success(emptyList()))

    val uiState: StateFlow<ProductsState> = _uiState

    init {
        scrapData()
    }

    private fun scrapData() {
        APIService.ProductService.getScrappedData().enqueue(
            object : retrofit2.Callback<List<ScrappedProduct>> {
                override fun onResponse(
                    call: Call<List<ScrappedProduct>>,
                    response: Response<List<ScrappedProduct>>
                ) {
                    response.body()?.let {
                        _uiState.value = ProductsState.Success(it)
                    }
                }

                override fun onFailure(call: Call<List<ScrappedProduct>>, t: Throwable) {
                    _uiState.value = ProductsState.Error(t)
                }

            })
    }

    sealed class ProductsState {
        data class Success(val products: List<ScrappedProduct>): ProductsState()
        data class Error(val exception: Throwable): ProductsState()
    }
}

