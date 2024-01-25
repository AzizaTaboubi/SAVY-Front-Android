package com.example.savy.view.product

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savy.ProductOnListProdClick
import com.example.savy.ProductsAdapter
import com.example.savy.databinding.RecyclerviewNewprodBinding
import com.example.savy.model.Products
import com.example.savy.viewmodel.UsedProductsViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class FilterProductsByTypeActivity: AppCompatActivity(), ProductOnListProdClick {
    private var binding: RecyclerviewNewprodBinding? = null
    private lateinit var productViewModel: UsedProductsViewModel
    private lateinit var adapter: ProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerviewNewprodBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)

        productViewModel = ViewModelProvider(this)[UsedProductsViewModel::class.java]
        val type: String = intent.getStringExtra("type") ?: ""
        supportActionBar?.title = type.replaceFirstChar { it.uppercase() }

        productViewModel.filterByType(type)

        val prods = ArrayList<Products>()

        binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        adapter = ProductsAdapter(prods, this)
        binding?.recyclerViewProduct?.adapter = adapter

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.uiState.collect { uiState ->
                    // New value received
                    when (uiState) {
                        is UsedProductsViewModel.ProductsState.Success ->
                            showProducts(uiState.products)
                        is UsedProductsViewModel.ProductsState.Error ->
                            Toast.makeText(this@FilterProductsByTypeActivity, uiState.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }

    private fun showProducts(products: List<Products>) {
        adapter.setList(products)
    }

    override fun onItemClick(scrappedProduct: Products) {

    }
}