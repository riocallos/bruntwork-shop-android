package com.riocallos.bruntwork.shop.features.products

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseAdapter
import com.riocallos.bruntwork.shop.base.BaseViewHolder
import com.riocallos.bruntwork.shop.databinding.ItemProductBinding
import com.riocallos.bruntwork.shop.domain.models.Product

class ProductsAdapter constructor(context: Context) : BaseAdapter<Product>(context, BaseDiffCallback()) {

    interface OnActionListener {
        fun add(position: Int, product: Product)
        fun remove(position: Int, product: Product)
    }

    var onActionListener: OnActionListener? = null

    internal inner class ItemCartViewHolder(private val binding: ItemProductBinding) :
        BaseViewHolder<Product>(binding.root) {
        override fun bind(item: Product, position: Int) {
            binding.product = item
            binding.quantityContainer.setOnClickListener {
                onActionListener?.let {
                    it.add(position, item)
                }
            }
            binding.minus.setOnClickListener {
                onActionListener?.let {
                    it.remove(position, item)
                }
            }
            binding.plus.setOnClickListener {
                onActionListener?.let {
                    it.add(position, item)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun createItemHolder(viewGroup: ViewGroup, itemType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProductBinding>(
            layoutInflater,
            R.layout.item_product,
            viewGroup,
            false
        )
        return ItemCartViewHolder(binding)
    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, data: Product, position: Int) {
        (viewHolder as ItemCartViewHolder).bind(data, position)
    }
}