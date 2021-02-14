package com.riocallos.bruntwork.shop.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(protected val context: Context, diffCallback: DiffUtil.ItemCallback<T>) :
        ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {
    class BaseDiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    protected var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            bindItemViewHolder(holder, item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createItemHolder(parent, viewType)
    }

    protected abstract fun createItemHolder(viewGroup: ViewGroup, itemType: Int): RecyclerView.ViewHolder
    protected abstract fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, data: T, position: Int)
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * clear adapter data
     */
    fun clear() {
        submitList(listOf<T>())
        notifyDataSetChanged()
    }
}