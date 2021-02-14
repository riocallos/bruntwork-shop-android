package com.riocallos.bruntwork.shop.features.products

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseAdapter
import com.riocallos.bruntwork.shop.base.BaseViewModelActivity
import com.riocallos.bruntwork.shop.databinding.ActivityProductsBinding
import com.riocallos.bruntwork.shop.databinding.ItemCategoryBinding
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.features.cart.CartActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class ProductsActivity : BaseViewModelActivity<ActivityProductsBinding, ProductsViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_products

    private val productsAdapter by lazy {
        ProductsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupVmObservers()
    }

    override fun onResume() {
        super.onResume()
        setupVm()
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupViews() {
        binding.toolbar.back.visibility = View.GONE

        binding.toolbar.cart.setOnClickListener {
            startActivity(Intent(this@ProductsActivity, CartActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@ProductsActivity).toBundle())
        }

        binding.swipeRefreshLayout.isRefreshing = true
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.empty.visibility = View.GONE
            binding.categoriesScrollView.visibility = View.GONE
            binding.productsRecyclerView.visibility = View.GONE
            viewModel.localGetAllProducts()
        }

        productsAdapter.onActionListener = object : ProductsAdapter.OnActionListener {
            override fun add(position: Int, product: Product) {
                viewModel.addToCart(position, product)
            }

            override fun remove(position: Int, product: Product) {
                viewModel.removeFromCart(position, product)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView
            .apply {
                this.layoutManager = layoutManager
                post {
                    this.adapter = productsAdapter
                }
            }

    }

    private fun setupVmObservers() {
        viewModel.state
            .toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(schedulers.ui())
            .subscribeBy(
                onNext = { state ->
                    handleState(state)
                },
                onError = {
                    it.printStackTrace()
                }
            ).apply {
                disposables.add(this)
            }

    }

    private fun setupVm() {
        viewModel.getCartQuantity()
        viewModel.localGetAllProducts()
    }

    private fun handleState(state: ProductsState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            is ProductsState.ShowCartQuantity -> {
                binding.toolbar.cartQuantity.text = state.quantity
                binding.toolbar.cartQuantity.visibility = View.VISIBLE
            }
            ProductsState.HideCartQuantity -> {
                binding.toolbar.cartQuantity.visibility = View.GONE
            }
            ProductsState.ShowCategories -> {
                showCategories()
            }
            is ProductsState.ShowProducts -> {
                showProducts(state.products)
            }
            is ProductsState.UpdateProduct -> {
                productsAdapter.notifyItemChanged(state.position)
            }
        }
    }

    private fun showCategories() {
        binding.categoriesContainer.removeAllViews()
        val categories = arrayOf("All", "Jackets", "Blazers", "Tees")
        for(category in categories) {

            val inflater = LayoutInflater.from(this)
            val categoryBinding = ItemCategoryBinding.inflate(inflater, binding.categoriesContainer, false);

            categoryBinding.name.text = category

            categoryBinding.cancel.visibility = if(viewModel.isCategorySelected(category)) View.VISIBLE else View.GONE
            categoryBinding.card.setCardBackgroundColor(if(viewModel.isCategorySelected(category)) Color.parseColor("#FFC7DE") else Color.TRANSPARENT)
            categoryBinding.container.setOnClickListener { _ -> viewModel.selectCategory(category) }
            categoryBinding.cancel.setOnClickListener { _ -> viewModel.selectCategory(category) }

            binding.categoriesContainer.addView(categoryBinding.card)
        }
        binding.categoriesScrollView.visibility = View.VISIBLE
    }

    private fun showProducts(products: List<Product>) {
        binding.swipeRefreshLayout.isRefreshing = false
        if(products.isNotEmpty()) {
            binding.empty.visibility = View.GONE
            binding.productsRecyclerView.visibility =  View.VISIBLE
            productsAdapter.submitList(products)
            productsAdapter.notifyDataSetChanged()
        } else {
            binding.empty.visibility = View.VISIBLE
            binding.productsRecyclerView.visibility =  View.GONE
        }
    }

}