package com.example.savy

import com.example.savy.model.Products

interface UserProductListener {
    fun onEditProduct(product: Products)
    fun onDeleteProduct(product: Products)
}