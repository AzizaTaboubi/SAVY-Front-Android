package com.example.savy.view.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.savy.databinding.UsedFragmentBinding

class UsedFragment  : Fragment(){

    private var binding: UsedFragmentBinding? = null
    private var price = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsedFragmentBinding.inflate(inflater, container, false)
        binding?.seekBarPrice?.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Calculate the price based on the progress value.
                price = progress * 50

                // Display the price in a TextView.
                binding?.fromTxt?.text = "Starting from $price DT"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do nothing.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do nothing.
            }
        })

        binding?.searchBtn?.setOnClickListener {
            val type: String = binding?.modelspin?.selectedItem?.toString() ?: "tablette"
            val marque: String = binding?.brandspin?.selectedItem?.toString() ?: "Samsung"
            val year: Int? = try {
                binding?.yearSpinner?.selectedItem?.toString()?.toInt()
            } catch (e: Exception) {
                null
            }
            var city: String? = binding?.gouverspin?.selectedItem?.toString()
            if (binding?.gouverspin?.selectedItemPosition == 0) city = null
            val intent = Intent(context, UsedProductsActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("marque", marque)
            intent.putExtra("annee", year)
            intent.putExtra("city", city)
            intent.putExtra("prix", price)
            startActivity(intent)
        }



        binding?.browseAll?.setOnClickListener{
            val intent = Intent(context, UsedProductsActivity::class.java)
            startActivity(intent)
        }



        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //

    }
}