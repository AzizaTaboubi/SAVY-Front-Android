package com.example.savy.view.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.savy.R
import com.example.savy.databinding.NewFragmentBinding

class FilterProductByTypeFragment  : Fragment(){

    private var binding: NewFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = NewFragmentBinding.inflate(inflater, container, false)

        binding?.phoneImageBtn?.setOnClickListener {
            val intent = Intent(context, FilterProductsByTypeActivity::class.java)
            intent.putExtra("type", "mobile")
            context?.startActivity(intent)
        }

        binding?.laptopImageBtn?.setOnClickListener {
            val intent = Intent(context, FilterProductsByTypeActivity::class.java)
            intent.putExtra("type", "laptop")
            context?.startActivity(intent)
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //

    }
}