package com.example.savy.view.product

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savy.ScrappedProductsAdapter
import com.example.savy.ScrappedProductOnListProdClick
import com.example.savy.R
import com.example.savy.databinding.RecyclerviewNewprodBinding
import com.example.savy.model.ScrappedProduct
import com.example.savy.viewmodel.ScrappedProductsViewModel
import kotlinx.coroutines.*


class NewProductActivity : AppCompatActivity(), ScrappedProductOnListProdClick {

    private lateinit var adapter: ScrappedProductsAdapter
    private lateinit var productViewModel: ScrappedProductsViewModel
    private var binding: RecyclerviewNewprodBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RecyclerviewNewprodBinding.inflate(layoutInflater)
        setContentView(binding?.root) //new_prod_scrap
        setSupportActionBar(binding?.toolbar)

        supportActionBar?.title = "New Products"

        productViewModel = ViewModelProvider(this).get(ScrappedProductsViewModel::class.java)


        val prods = ArrayList<ScrappedProduct>()

        binding?.recyclerViewProduct?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        adapter = ScrappedProductsAdapter(prods, this, this)
        binding?.recyclerViewProduct?.adapter = adapter

        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productViewModel.uiState.collect { uiState ->
                    // New value received
                    when (uiState) {
                        is ScrappedProductsViewModel.ProductsState.Success ->
                            showProducts(uiState.products)
                        is ScrappedProductsViewModel.ProductsState.Error ->
                            Toast.makeText(this@NewProductActivity, uiState.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }

    private fun showProducts(products: List<ScrappedProduct>) {
        val list = arrayListOf<ScrappedProduct>()
        for (i in 0 until 10){
            list.add(ScrappedProduct(i, "url$i", "", "title$i", "$i TND", ""))
        }
        adapter.setList(products)
    }

    override fun onItemClick(scrappedProduct: ScrappedProduct) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(scrappedProduct.url))
        startActivity(browserIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as? SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0 ?: "")
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}
/*
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

    binding.apply {
        recyclerView.adapter = restaurantRecyclerView
        viewModel.getAllRestaurants()
    }

    restaurantRecyclerView.onListItemClick = this

    binding.apply {
        viewModel.restaurantsLiveData.observe(viewLifecycleOwner,
            Observer {
                if (it != null) {
                    restaurantRecyclerView.setList(it)
                } else {
                    Toast.makeText(
                        context,
                        "Connection Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )

        viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if(it == "Server error, please try again later."){
                    Toast.makeText(
                        context,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}

override fun onItemClick(restaurant: Restaurant) {
    val action = RestaurantsFragmentDirections.actionRestaurantsFragmentToRestaurantDetailsFragment(restaurant.id)
    findNavController().navigate(action)
}*/
