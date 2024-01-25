package com.example.savy.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.savy.R
import com.example.savy.databinding.HomeFragmentBinding
import com.example.savy.view.product.NewProductActivity
import com.example.savy.view.product.UsedProductsActivity
import kotlinx.coroutines.*

class HomeFragment : Fragment(){

    private var binding: HomeFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding?.imgSwitcher?.setFactory {
            val newSwitcher = ImageView(context)
            newSwitcher.scaleType = ImageView.ScaleType.CENTER_CROP
            newSwitcher.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            newSwitcher
        }
        binding?.imgSwitcher?.setOnClickListener {
            val intent = Intent(context, NewProductActivity::class.java)
            context?.startActivity(intent)
        }

        binding?.showAllBtn?.setOnClickListener {
            val intent = Intent(context, UsedProductsActivity::class.java)
            context?.startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val images = listOf(
            R.drawable.pub_2,
            R.drawable.laptop_1,
            R.drawable.access,
            R.drawable.phone_1,
            )

        var currentIndex = 0
        binding?.imgSwitcher?.setImageResource(images[currentIndex])
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(2000)
                currentIndex = (currentIndex + 1) % images.size
                withContext(Dispatchers.Main) {
                    binding?.imgSwitcher?.setImageResource(images[currentIndex])
                }
            }
        }
//        val handler = Handler(Looper.getMainLooper())
//        handler.post(object : Runnable {
//            override fun run() {
//                currentIndex = (currentIndex + 1) % images.size
//                binding?.imgSwitcher?.setImageResource(images[currentIndex])
//                handler.postDelayed(this, 2000)
//            }
//        })
    }
}