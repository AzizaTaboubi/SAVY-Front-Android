package com.example.savy.view.profile

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
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.savy.R
import com.example.savy.model.LoginResponse
import com.example.savy.model.UpdateProfileBody
import com.example.savy.model.User
import com.example.savy.services.APIService
import com.example.savy.utils.Constant
import com.example.savy.view.login.ResetPwdActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class UpdateProfile: AppCompatActivity() {

    private lateinit var fullnameEdit : TextInputEditText
    private lateinit var fullnameLyt : TextInputLayout
    private lateinit var numtelEdit : TextInputEditText
    private lateinit var numtelLyt : TextInputLayout
    private lateinit var emailEdit : TextInputEditText
    private lateinit var emailLyt : TextInputLayout
    private lateinit var profilepic: ImageView
    private lateinit var btnConfirm : Button
    private lateinit var btnChangePwd : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_profile)
        //Val
        val context = this@UpdateProfile
        profilepic = findViewById(R.id.profilepic)
        fullnameEdit = findViewById(R.id.fullnameEdit)
        numtelEdit = findViewById(R.id.numtelEdit)
        emailEdit = findViewById(R.id.emailEdit)
        emailLyt = findViewById(R.id.emailLyt)
        numtelLyt = findViewById(R.id.numtelLyt)
        fullnameLyt = findViewById(R.id.fullnameLyt)
        btnConfirm = findViewById(R.id.btnUpdate)
        btnChangePwd = findViewById(R.id.btnChangePwd)

        //getting info from sharedpref
        val sharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_SESSION, MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", "")
        if (userData != null) {
            val user = Gson().fromJson(userData, User::class.java)
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.savy_logo)
            profilepic.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type="image/*"
                startForResult.launch(intent)
            }
            Glide.with(this).load(user.profilepic).apply(requestOptions).into(profilepic) //set profile picture
            emailEdit.setText(user.email)
            numtelEdit.setText(user.numTel)
            fullnameEdit.setText(user.fullname)
            btnChangePwd.setOnClickListener {
                val intent = Intent(context, ResetPwdActivity::class.java)
                startActivity(intent)
            }

            btnConfirm.setOnClickListener {
                APIService.UserService.updateProfile(
                    UpdateProfileBody(
                        emailEdit.text.toString(),
                        fullnameEdit.text.toString(),
                        numtelEdit.text.toString()
                    )
                ).enqueue(
                    object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.code() == 200) {
                                Toast.makeText(context, "Profile Updated ✅", Toast.LENGTH_SHORT).show()
                            } else if (response.code() == 400) {
                                println("status code is " + response.message())
                                showDialog(context, "Error ❌")
                            } else {
                                println("status code is " + response.code())
                                startActivity(Intent(context, ProfileFragment::class.java))
                                finish()
                            }
                        }
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            println("HTTP ERROR")
                            t.printStackTrace()
                        }
                    })
            }

        }
    }
    fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }
    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startForResult.launch(intent)
    }
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                profilepic.setImageURI(data?.data)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveSelectedImage29(data?.data)
                }else{
                    saveSelectedImage(data?.data)
                }
            }
        }
    private fun saveSelectedImage(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(getExternalFilesDir(null), "profile_pic.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Profile picture saved!", Toast.LENGTH_SHORT).show()

            val sharedPreference = getSharedPreferences("GENERAL_INFO", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", file)
            editor.putString("uri", uri.toString())
            editor.apply()
        }else{
            Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveSelectedImage29(uri: Uri?) {
        if (uri != null) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val fileName = "profile_pic.png"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$packageName")
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
                    Toast.makeText(this, "Profile picture saved!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    resolver.delete(uri, null, null)
                    Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }
}

