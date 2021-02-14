package com.riocallos.bruntwork.shop.features.cart

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseAdapter
import com.riocallos.bruntwork.shop.base.BaseViewHolder
import com.riocallos.bruntwork.shop.databinding.ItemCartBinding
import com.riocallos.bruntwork.shop.domain.models.Product

class CartAdapter constructor(context: Context) : BaseAdapter<Product>(context, BaseDiffCallback()) {

    interface OnActionListener {
        fun delete(position: Int, product: Product)
    }

    var onActionListener: OnActionListener? = null

    internal inner class ItemCartViewHolder(private val binding: ItemCartBinding) :
        BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product, position: Int) {
            binding.product = item
            binding.delete.setOnClickListener {
                onActionListener?.let {
                    it.delete(position, item)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun createItemHolder(viewGroup: ViewGroup, itemType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemCartBinding>(
            layoutInflater,
            R.layout.item_cart,
            viewGroup,
            false
        )
        return ItemCartViewHolder(binding)
    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, data: Product, position: Int) {
        (viewHolder as ItemCartViewHolder).bind(data, position)
    }
}