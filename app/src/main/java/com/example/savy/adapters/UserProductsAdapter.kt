package com.example.savy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.savy.R
import com.example.savy.UserProductListener
import com.example.savy.databinding.LayoutProductUserViewBinding
import com.example.savy.model.Products

class UserProductsAdapter(context: Context, var dataList: List<Products>, val listener: UserProductListener) :
    RecyclerView.Adapter<UserProductsAdapter.ViewHolder>() {
    private lateinit var editBtn: ImageButton
    private lateinit var delBtn: ImageButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutProductUserViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        val productName: TextView = holder.itemView.findViewById(R.id.productName)
        val productPrice: TextView = holder.itemView.findViewById(R.id.productPrice)


        val requestOptions = RequestOptions().placeholder(R.drawable.savy_logo)
        //Glide.with(holder.itemView.context).load(Constant.image_URL + data.image).error(R.drawable.savy_logo).into(holder.itemView.findViewById<ImageView>(R.id.imageProduit))
        editBtn = holder.itemView.findViewById(R.id.editBtn)
        delBtn = holder.itemView.findViewById(R.id.delBtn)
        editBtn.setOnClickListener {
            listener.onEditProduct(data)
        }
        delBtn.setOnClickListener {
            listener.onDeleteProduct(data)
        }
        productName.text = dataList[position].nom
        productPrice.text = dataList[position].prix.toString()
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    inner class ViewHolder(itemView: LayoutProductUserViewBinding) : RecyclerView.ViewHolder(itemView.root) {

    }


    // other methods

    fun setProducts(products: List<Products>) {
        dataList = products
        notifyDataSetChanged()
    }

}