package com.example.savy

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.savy.view.*
import com.example.savy.view.product.FilterProductByTypeFragment
import com.example.savy.view.product.UsedFragment
import com.example.savy.view.profile.ProfileFragment
import com.example.savy.view.profile.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setFullScreen(this@MainActivity)
        setContentView(R.layout.main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val iconColor = ContextCompat.getColorStateList(this, R.color.green_main)
        bottomNavigationView.setItemIconTintList(iconColor)
        val bgColor = ContextCompat.getColor(this, R.color.black)
        val textColor = ContextCompat.getColorStateList(this, R.color.green_main)
        bottomNavigationView.setItemTextColor(textColor)
        bottomNavigationView.setBackground(ColorDrawable(bgColor))
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.nav_new -> {
                    val fragment = FilterProductByTypeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.nav_used -> {
                    val fragment = UsedFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.nav_profile -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                R.id.nav_settings -> {
                    val fragment = SettingsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit()
                    true
                }
                else -> false
            }
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