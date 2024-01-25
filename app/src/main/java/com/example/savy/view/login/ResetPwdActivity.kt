package com.example.savy.view.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.savy.R
import com.example.savy.model.ChangePwdBody
import com.example.savy.model.LoginResponse
import com.example.savy.services.APIService
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPwdActivity : AppCompatActivity(){

    private lateinit var pwdEdit: TextInputEditText
    private lateinit var pwdLyt: TextInputLayout
    private lateinit var pwdcEdit: TextInputEditText
    private lateinit var pwdcLyt: TextInputLayout
    private lateinit var nextbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset_password)
        //VAR
        val context = this@ResetPwdActivity
        supportActionBar?.hide()
        setFullScreen(context)
        val email = intent.getStringExtra("email")
        println(email)
        //INIT components
        pwdEdit = findViewById(R.id.pwdEdit)
        pwdLyt = findViewById(R.id.pwdLyt)
        pwdcEdit = findViewById(R.id.pwdcEdit)
        pwdcLyt = findViewById(R.id.pwdcLyt)
        nextbtn = findViewById(R.id.btnNext)
        nextbtn.setOnClickListener{
            if(comparePasswords(pwdEdit.text.toString(), pwdcEdit.text.toString())){

                APIService.UserService.changePwd(
                    ChangePwdBody(
                    pwdEdit.text.toString(),
                    // email.toString()
                    "aziza.taboubi@esprit.tn")
                ).enqueue( object :
                    Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(this@ResetPwdActivity, "Password Changed ✅", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(this@ResetPwdActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 400) {
                            showDialog(context,"Error")
                        }
                        else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        println("HTTP ERROR")
                        t.printStackTrace()}

                })
            } else {
                showDialog(context,"Passwords do not match. Please try again. ❌")
            }
        }
    }
    fun comparePasswords(pwd:String , cPwd: String):Boolean{
        if(pwd.compareTo(cPwd) == 0 ){
            return true
        }
        return false
    }
    private fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(this@ResetPwdActivity)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
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