package com.example.savy

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.savy.model.ScrappedProduct


class ScrappedProductsAdapter(var productsList:List<ScrappedProduct> = emptyList(), val context:Context , var onListItemClick: ScrappedProductOnListProdClick? = null):
    RecyclerView.Adapter<ScrappedProductsAdapter.CustomViewHolder>(), Filterable {

    var productsFilteredList: List<ScrappedProduct> = ArrayList()

    init {
        productsFilteredList = productsList
    }

    override fun getFilter(): Filter {
       return  object : Filter() {
           override fun performFiltering(constraint: CharSequence?): FilterResults {
               var charSearch = constraint.toString()
               if (charSearch.isEmpty()) {
                    productsFilteredList = productsList
               } else {
                   var resultList = ArrayList<ScrappedProduct>()
                   for (scrappedProduct in productsList) {
                       if (scrappedProduct.title.lowercase().contains(charSearch.lowercase())) {
                           resultList.add(scrappedProduct)
                       }
                   }
                   productsFilteredList =resultList
               }
               val filterResult = FilterResults()
               filterResult.values = productsFilteredList
               return filterResult
           }

           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               productsFilteredList = results?.values as ArrayList<ScrappedProduct>
               notifyDataSetChanged()
           }
        }
    }


    fun setList(productsList: List<ScrappedProduct>) {
        this.productsList = productsList
        this.productsFilteredList = productsList
        notifyDataSetChanged()
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productPic: ImageView = itemView.findViewById(R.id.productPic)
        var productName: TextView = itemView.findViewById(R.id.productName)
        var productPrice: TextView = itemView.findViewById(R.id.productPrice)

        fun bind(scrappedProduct: ScrappedProduct){
            //itemImage.setImageResource(R.drawable.restaurant_item)

            Glide.with(context).load(scrappedProduct.image).into(productPic)
            productName.text = scrappedProduct.title
            productPrice.text = scrappedProduct.price

            itemView.setOnClickListener{
                onListItemClick?.onItemClick(scrappedProduct)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_prod_view, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount() = productsFilteredList.size //productsFilteredList.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val scrappedProduct =productsFilteredList[position]
        var scrappedProduct: ScrappedProduct = productsFilteredList.get(position)
        holder.bind(scrappedProduct)
    }

}









/*
RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val product_name = itemView.findViewById<TextView>(R.id.productName)
        val product_price = itemView.findViewById<TextView>(R.id.productPrice)
        val product_pic = itemView.findViewById<TextView>(R.id.productPic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_newprod, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return prodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val data : ScrappedProduct = prodList[position]
        holder.product_name.text = data.title
        holder.product_price.text = data.price
       // holder.product_pic.setImageResource(data.image)
    }
}*/
