package com.example.savy.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.savy.R
import com.example.savy.model.EmailBody
import com.example.savy.model.LoginResponse
import com.example.savy.services.APIService
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity(){
    private lateinit var emailotpText: TextInputEditText
    private lateinit var confirmBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)
        emailotpText= findViewById(R.id.emailotpText)
        confirmBtn= findViewById<Button>(R.id.getotpBtn)
        confirmBtn.setOnClickListener {

            APIService.UserService.sendOtp(EmailBody(emailotpText.text.toString())).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(this@ForgotPasswordActivity, OtpActivity::class.java).apply {
                                putExtra("email",  emailotpText.text.toString())

                            }
                            println(emailotpText.text.toString())
                            startActivity(intent)
                            finish()
                        }
                        else if(response.code() == 404) {
                            showDialog(this@ForgotPasswordActivity,"Use an existing mail ❌")
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
    private fun showDialog(activityName: Context, message:String){
        val builder = AlertDialog.Builder(activityName)
        builder.setTitle("Caution ⚠️")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

}