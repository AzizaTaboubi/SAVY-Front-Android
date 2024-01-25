package com.example.savy.view.profile

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.savy.R
import com.example.savy.model.User
import com.example.savy.utils.Constant
import com.example.savy.view.product.UserProductsActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileFragment : Fragment(){

    private lateinit var profileImg: ImageView
    private lateinit var name_txt: TextView
    private lateinit var updateBtn: Button
    private lateinit var productsBtn: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{



        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        //INIT
        profileImg = view.findViewById(R.id.profileImg)
        name_txt = view.findViewById(R.id.name_txt)
        updateBtn = view.findViewById(R.id.updateBtn)
        productsBtn = view.findViewById(R.id.productsBtn)
        //Var
        var FieldsState = false
        val context = requireContext()
        //getting info from sharedpref
        val sharedPreferences =
            context.getSharedPreferences(Constant.SHARED_PREF_SESSION, Context.MODE_PRIVATE)
        val userData = sharedPreferences.getString("USER_DATA", null)
        if (userData != null) {
            val user = Gson().fromJson(userData, User::class.java)
            val picasso = Picasso.get()
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.savy_logo)
            //picasso.load(user.profilepic).placeholder(R.drawable.savy_logo).error(R.drawable.forgot).into(profileImg)
            profileImg.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type="image/*"
                startForResult.launch(intent)
            }
            Glide.with(requireContext()).load(user.profilepic).apply(requestOptions).into(profileImg) //set profile picture
            name_txt.setText(user.fullname)
            println(user)
            updateBtn.setOnClickListener {
                val intent = Intent(context, UpdateProfile::class.java)
                startActivity(intent)
            }
            productsBtn.setOnClickListener{
                val intent = Intent(context, UserProductsActivity::class.java)
                startActivity(intent)
            }

        }

        return view
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
                profileImg.setImageURI(data?.data)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    saveSelectedImage29(data?.data)
                }else{
                    saveSelectedImage(data?.data)
                }
            }
        }
    private fun saveSelectedImage(uri: Uri?) {
        if (uri != null) {
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val file = File(requireActivity().getExternalFilesDir(null), "profile_pic.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(requireContext(), "Profile picture saved!", Toast.LENGTH_SHORT).show()

            val sharedPreference =  requireActivity().getSharedPreferences("GENERAL_INFO", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            val uri = FileProvider.getUriForFile(requireContext(), "${requireActivity().packageName}.provider", file)
            editor.putString("uri", uri.toString())
            editor.apply()
        }else{
            Toast.makeText(requireContext(), "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveSelectedImage29(uri: Uri?) {
        if (uri != null) {
            val inputStream = requireActivity().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val fileName = "profile_pic.png"
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/${requireActivity()}.packageName")
            }
            val resolver = requireActivity().applicationContext.contentResolver
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
                    Toast.makeText(requireContext(), "Profile picture saved!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    resolver.delete(uri, null, null)
                    Toast.makeText(requireContext(), "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Failed to save profile picture!", Toast.LENGTH_SHORT).show()
        }
    }
}