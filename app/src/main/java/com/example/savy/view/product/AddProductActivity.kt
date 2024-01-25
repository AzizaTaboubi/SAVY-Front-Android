package com.example.savy.view.product

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.request.RequestOptions
import com.example.savy.R
import com.example.savy.model.AddProduct
import com.example.savy.model.ProductResponse
import com.example.savy.model.User
import com.example.savy.services.APIService
import com.example.savy.utils.Constant
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddProductActivity : AppCompatActivity() {
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
        contextt = this@AddProductActivity
        val sharedPreferences =
            contextt.getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        setContentView(R.layout.add_product)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.savy_logo)
        val context = this@AddProductActivity
        supportActionBar?.hide()
        setFullScreen(context)
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
        btnAdd = findViewById(R.id.btnAdd)


        productpic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startForResult.launch(intent)
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
                Toast.makeText(
                    this@AddProductActivity,
                    "selectedItem: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
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
                Toast.makeText(
                    this@AddProductActivity,
                    "selectedItem: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
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
                Toast.makeText(
                    this@AddProductActivity,
                    "selectedItem: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
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
                Toast.makeText(
                    this@AddProductActivity,
                    "selectedItem: $selectedItem",
                    Toast.LENGTH_SHORT
                ).show()
                gouveer = "$selectedItem"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO("Not yet implemented")
            }
        }


        btnAdd.setOnClickListener {
            val sharedPreferences =
                this@AddProductActivity.getSharedPreferences(
                    Constant.SHARED_PREF_SESSION,
                    Context.MODE_PRIVATE
                )
            val userData = sharedPreferences.getString("USER_DATA", "")
            val user = Gson().fromJson(userData, User::class.java)
            APIService.ProductService.addProduct(
                AddProduct(
                    user._id,
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
                .enqueue(object : Callback<ProductResponse> {
                    override fun onResponse(
                        call: Call<ProductResponse>,
                        response: Response<ProductResponse>
                    ) {
                        if (response.code() == 200) {
//                            val intent = Intent(activity, products::class.java)
//                            startActivity(intent)
//                            activity?.finish()
//                            println("status code is " + response.code())
//                            showDialog(contextt, "Product Added ")
                        } else if (response.code() == 409) {
//                            showDialog(contextt, "Error ")
                        } else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                        println("HTTP ERROR")
                        t.printStackTrace()
                    }
                })
        }
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                productpic.setImageURI(data?.data)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveSelectedImage29(data?.data)
                } else {
                    saveSelectedImage(data?.data)
                }
            }
        }

    private fun saveSelectedImage(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(getExternalFilesDir(null), "productpic.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Product picture saved!", Toast.LENGTH_SHORT).show()

            val sharedPreference = getSharedPreferences("GENERAL_INFO", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            editor.putString("uri", uri.toString())
            editor.apply()
        } else {
            Toast.makeText(this, "Failed to save product picture!", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveSelectedImage29(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val fileName = "productpic.png"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/$packageName"
                )
            }
            val resolver = applicationContext.contentResolver
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.Images.Media.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(fileName)
            resolver.delete(contentUri, selection, selectionArgs)
            val uri = resolver.insert(contentUri, contentValues)

            if (uri != null) {
                try {
                    val outputStream = resolver.openOutputStream(uri)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream?.flush()
                    outputStream?.close()
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                    Toast.makeText(this, "Product picture saved!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    resolver.delete(uri, null, null)
                    Toast.makeText(this, "Failed to save product picture!", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Failed to save product picture!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to save product picture!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }
}