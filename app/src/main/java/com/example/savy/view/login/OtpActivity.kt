package com.example.savy.view.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chaos.view.PinView
import com.example.savy.R
import com.example.savy.model.Otp
import com.example.savy.model.LoginResponse
import com.example.savy.services.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : AppCompatActivity(){
    private lateinit var pinView: PinView
    private lateinit var nextBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_activity)
        //hide Toolbar
        supportActionBar?.hide()
        setFullScreen(this@OtpActivity)
        setContentView(R.layout.otp_activity)
        // init components
        pinView = findViewById(R.id.pinView)
        nextBtn = findViewById(R.id.confirmOtp)
        var finalOtp = ""
        pinView.requestFocus()
        val inputMethod : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        pinView.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length == 4){
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(pinView.windowToken, 0)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                finalOtp = pinView.text.toString()
            }
        })

        nextBtn.setOnClickListener{
            APIService.UserService.confirmOtp(
                Otp(finalOtp)
            ).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.code() == 200) {
                            Toast.makeText(this@OtpActivity, "OTP Accepted ✅", Toast.LENGTH_SHORT).show()
                            val intent =
                                Intent(this@OtpActivity, ResetPwdActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 400) {
                            val builder = AlertDialog.Builder(this@OtpActivity)
                            builder.setTitle("Caution ⚠️")
                            builder.setMessage("Wrong OTP ❌")
                            builder.setPositiveButton("OK", null)
                            val dialog = builder.create()
                            dialog.show()
                        }
                        else {
                            println("status code is " + response.code())
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        println("HTTP ERROR")
                        t.printStackTrace()}

                }
            )
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