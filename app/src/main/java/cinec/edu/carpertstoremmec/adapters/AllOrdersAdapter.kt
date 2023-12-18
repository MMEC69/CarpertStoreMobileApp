package cinec.edu.carpertstoremmec.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.data.order.Order
import cinec.edu.carpertstoremmec.data.order.OrderStatus
import cinec.edu.carpertstoremmec.data.order.getOrderStatus
import cinec.edu.carpertstoremmec.databinding.OrderItemBinding

class AllOrdersAdapter: Adapter<AllOrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: OrderItemBinding): ViewHolder(binding.root){
        fun bind(order: Order){
            binding.apply {
                tvOrderId.text = order.orderId.toString()
                tvOrderDate.text = order.date
                val resource = itemView.resources

                val colorDrawable = when (getOrderStatus(order.orderStatus)){
                    is OrderStatus.Ordered -> {
                        ColorDrawable(resource.getColor(R.color.g_orange_yellow))
                    }
                    is OrderStatus.Confirmed ->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Delivered ->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Shipped ->{
                        ColorDrawable(resource.getColor(R.color.g_green))
                    }
                    is OrderStatus.Canceled ->{
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                    is OrderStatus.Returned ->{
                        ColorDrawable(resource.getColor(R.color.g_red))
                    }
                }

                imageOrderState.setImageDrawable(colorDrawable)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.products == newItem.products
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        return OrderViewHolder(
            OrderItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllOrdersAdapter.OrderViewHolder, position: Int) {
        val order = differ.currentList[position]
        holder.bind(order)

        holder.bind(order)

        holder.itemView.setOnClickListener {
            onClick?.invoke(order)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    var onClick: ((Order) -> Unit)?=null



}