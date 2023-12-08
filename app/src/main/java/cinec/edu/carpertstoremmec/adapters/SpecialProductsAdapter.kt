package cinec.edu.carpertstoremmec.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cinec.edu.carpertstoremmec.data.Product
import cinec.edu.carpertstoremmec.databinding.SpecialRvItemBinding
import com.bumptech.glide.Glide

class SpecialProductsAdapter:RecyclerView.Adapter<SpecialProductsAdapter.SpecialProductsViewHolder>() {

    inner class SpecialProductsViewHolder(private val binding: SpecialRvItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(product: Product){
                binding.apply {
                    Glide.with(itemView).load(product.images[0]).into(imageSpecialRVItem)
                    product.offerPercentage?.let {
                        val remainingPricePercentage = 1f - it
                        val priceAfterOffer = remainingPricePercentage * product.price
                        tvSpecialProductPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"

                    }
                    if (product.offerPercentage == null)
                        tvSpecialProductPrice.visibility = View.INVISIBLE
                    tvSpecialProductName.text = product.name
                    //tvSpecialProductPrice.text = product.price.toString()
                }
            }
        }

    val diffCallback = object: DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialProductsViewHolder {
        return SpecialProductsViewHolder(
            SpecialRvItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SpecialProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}