package com.example.savy.view.product

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savy.R
import com.example.savy.UserProductListener
import com.example.savy.adapters.UserProductsAdapter
import com.example.savy.model.GetByID
import com.example.savy.model.Products
import com.example.savy.model.ProduitResponse
import com.example.savy.model.User
import com.example.savy.services.APIService
import com.example.savy.utils.Constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProductsActivity : AppCompatActivity(), UserProductListener {
    private lateinit var produitRV: RecyclerView
    private lateinit var addBtn:ImageButton
    private lateinit var productAdapter : UserProductsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {super.onCreate(savedInstanceState)
        setContentView(R.layout.user_products)
        produitRV = findViewById(R.id.produitRV)

        val sharedPreferences =  this@UserProductsActivity.getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", "")
        addBtn = findViewById(R.id.addBtn)

        print(userData)

        if (userData != null) {
            val user = Gson().fromJson(userData, User::class.java)
            APIService.ProductService.getAll(user._id)
                .enqueue(

                    object : Callback<ProduitResponse> {
                        override fun onResponse(

                            call: Call<ProduitResponse>,
                            response: Response<ProduitResponse>
                        ) {
                            print(response.toString())
                            if (response.code() == 200) {
                                val products = response.body()?.Products
                                println(response.body().toString())
                                println(products.toString())
                                if(products != null) {
                                    productAdapter.dataList = products
                                    productAdapter.notifyDataSetChanged()
                                }else{

                                    println("status code is " + response.code())

                                }

                            } else {
                                println("status code is " + response.code())
                            }

                        }

                        override fun onFailure(
                            call: Call<ProduitResponse>,
                            t: Throwable
                        ) {
                            println("HTTP ERROR")
                            t.printStackTrace()
                        }

                    }
                )

            addBtn.setOnClickListener {
                val intent = Intent(this@UserProductsActivity, AddProductActivity::class.java)
                startActivity(intent)
            }
            /*editBtn.setOnClickListener {
                val intent = Intent(this@products, updateused_products::class.java)
                startActivity(intent)
                finish()
            }*/
        }
        produitRV.layoutManager = LinearLayoutManager(this@UserProductsActivity)
        productAdapter = UserProductsAdapter(this@UserProductsActivity,listOf(), this)
        produitRV.adapter = productAdapter
    }

    override fun onEditProduct(product: Products) {
        Toast.makeText(this, "Editing ${product.nom}", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteProduct(product: Products) {
        Toast.makeText(this, "Deleting ${product.nom}", Toast.LENGTH_SHORT).show()
    }
}