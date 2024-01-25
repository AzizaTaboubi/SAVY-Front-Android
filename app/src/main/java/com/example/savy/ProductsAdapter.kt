package com.example.savy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.savy.model.Products


class ProductsAdapter(var productsList:List<Products> = emptyList(), var onListItemClick: ProductOnListProdClick? = null):
    RecyclerView.Adapter<ProductsAdapter.CustomViewHolder>(), Filterable {

    var productsFilteredList: List<Products> = ArrayList()

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
                   var resultList = ArrayList<Products>()
                   for (scrappedProduct in productsList) {
                       if (scrappedProduct.nom.lowercase().contains(charSearch.lowercase())) {
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
               productsFilteredList = results?.values as ArrayList<Products>
               notifyDataSetChanged()
           }
        }
    }


    fun setList(productsList: List<Products>) {
        this.productsList = productsList
        this.productsFilteredList = productsList
        notifyDataSetChanged()
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productPic: ImageView = itemView.findViewById(R.id.productPic)
        var productName: TextView = itemView.findViewById(R.id.productName)
        var productPrice: TextView = itemView.findViewById(R.id.productPrice)

        fun bind(scrappedProduct: Products){
            //itemImage.setImageResource(R.drawable.restaurant_item)
            productName.text = scrappedProduct.nom
            productPrice.text = scrappedProduct.prix.toString()

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
        var scrappedProduct: Products = productsFilteredList.get(position)
        holder.bind(scrappedProduct)
    }

}

