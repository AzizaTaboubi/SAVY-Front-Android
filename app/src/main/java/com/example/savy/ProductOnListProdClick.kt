package com.example.savy

import com.example.savy.model.Products
import com.example.savy.model.ScrappedProduct

interface ProductOnListProdClick {
    fun onItemClick(scrappedProduct: Products)
}