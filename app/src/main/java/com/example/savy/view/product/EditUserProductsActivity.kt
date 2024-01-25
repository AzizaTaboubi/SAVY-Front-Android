package com.example.savy.view.product

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.request.RequestOptions
import com.example.savy.R
import com.example.savy.model.Products
import com.example.savy.model.UpdateProduct
import com.example.savy.services.APIService
import com.example.savy.utils.Constant
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserProductsActivity : AppCompatActivity() {
    private lateinit var pdt: Products
    private lateinit var productpic: ImageView
    private lateinit var txtName: TextInputEditText
    private lateinit var txtLayoutName: TextInputLayout
    private lateinit var txtPrice: TextInputEditText
    private lateinit var txtLayoutPrice: TextInputLayout
    private lateinit var txtDiscount: TextInputEditText
    private lateinit var txtLayoutDiscount: TextInputLayout
    private lateinit var txtYear: TextInputEditText
    private lateinit var txtDescription: TextInputEditText
    private lateinit var txtLayoutDescription: TextInputLayout
    private lateinit var spinnerModel: Spinner
    private lateinit var spinnerBrand: Spinner
    private lateinit var spinnerState: Spinner
    private lateinit var spinnergouver: Spinner
    private lateinit var btnAdd: Button
    private lateinit var model: String
    private lateinit var brand: String
    private lateinit var statee: String
    private lateinit var gouveer: String
    lateinit var contextt: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contextt = this@EditUserProductsActivity
        val sharedPreferences =
            contextt.getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        setContentView(R.layout.add_product)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.savy_logo)
        val context = this@EditUserProductsActivity
        supportActionBar?.hide()
        setFullScreen(context)
//        pdt = arguments?.getParcelable<Products>("product")!!
        productpic = findViewById(R.id.productpic)
        txtName = findViewById(R.id.txtName)
        txtLayoutName = findViewById(R.id.txtLayoutName)
        txtPrice = findViewById(R.id.txtPrice)
        txtLayoutPrice = findViewById(R.id.txtLayoutPrice)
        txtDiscount = findViewById(R.id.txtDiscount)
        txtLayoutDiscount = findViewById(R.id.txtLayoutDiscount)
        txtYear = findViewById(R.id.txtYear)
        txtDescription = findViewById(R.id.txtDescription)
        txtLayoutDescription = findViewById(R.id.txtLayoutDescription)
        spinnerModel = findViewById(R.id.spinnerModel)
        spinnerBrand = findViewById(R.id.spinnerBrand)
        spinnerState = findViewById(R.id.spinnerState)
        spinnergouver = findViewById(R.id.spinnergouver)
        btnAdd  = findViewById(R.id.btnAdd)

//        Picasso.get().load(Constant.image_URL + pdt.image).into(productpic)
//        txtName.text = pdt.nom
//        txtPrice.text = pdt.prix.toString()
//        txtDiscount = pdt.promo
//        txtYear.text = pdt.annee
//        txtDescription = pdt.description
//        spinnerModel = pdt.type
//        spinnerBrand = pdt.marque
//        spinnerState = pdt.etat
//        spinnergouver = pdt.city


        productpic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
//            startForResult.launch(intent)
        }


        val models = arrayOf(
            "tablette",
            "camera",
            "console",
            "desktop",
            "mouse",
            "laptop",
            "mobile",
            "keyboard",
            "smartwatch",
            "tv",
            "fridge",
            "other"
        )
        val brands = arrayOf(
            "Samsung",
            "Apple",
            "LG",
            "Asus",
            "MSI",
            "Shark",
            "Huawei",
            "Xioami",
            "Toshiba",
            "Dell",
            "NewStar",
            "Other"
        )

        val state = arrayOf(
            "Nouveau",
            "Occasion",
            "Etat neuf ",
            "Jamais servi "
        )

        val gouver = arrayOf(
            "Ariana",
            "Beja",
            "Ben Arous",
            "Bizerte",
            "Gabes",
            "Gafsa",
            "Jendouba",
            "Kairouan",
            "Kasserine",
            "Kebili",
            "Kef",
            "Mahdia",
            "Manouba",
            "Medenine",
            "Monastir",
            "Nabeul",
            "Sfax",
            "Sidi Bouzid",
            "Siliana",
            "Sousse",
            "Tataouine",
            "Tozeur",
            "Tunis",
            "Zaghouan"


        )

        spinnerModel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = models[position]
                Toast.makeText(this@EditUserProductsActivity, "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                model = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }



        spinnerBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = brands[position]
                Toast.makeText(this@EditUserProductsActivity, "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                brand = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }


        spinnerState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = state[position]
                Toast.makeText(this@EditUserProductsActivity, "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                statee = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }




        spinnergouver.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = gouver[position]
                Toast.makeText(this@EditUserProductsActivity, "selectedItem: $selectedItem", Toast.LENGTH_SHORT).show()
                gouveer = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }



        btnAdd.setOnClickListener {
            APIService.ProductService.update(
                UpdateProduct(
                    txtName.text.toString(),
                    txtPrice.text.toString().toInt(),
                    productpic.toString(),
                    txtDiscount.text.toString().toInt(),
                    statee,
                    brand,
                    txtYear.text.toString().toInt(),
                    txtDescription.text.toString(),
                    model,
                    gouveer,
                )
            )
                .enqueue(object : Callback<UpdateProduct> {
                    override fun onResponse(
                        call: Call<UpdateProduct>,
                        response: Response<UpdateProduct>
                    ) {
                        if (response.code() == 200) {
//                            val intent = Intent(activity, products::class.java)
//                            startActivity(intent)
//                            activity?.finish()
//                            println("status code is " + response.code())
//                            showDialog(contextt, "Product updated ")
                        } else if (response.code() == 409) {
//                            showDialog(contextt, "Error ")
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<UpdateProduct>, t: Throwable) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }
                })
        }





    }

    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}