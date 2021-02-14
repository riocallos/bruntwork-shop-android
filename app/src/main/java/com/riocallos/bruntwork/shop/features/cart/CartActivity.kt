package com.riocallos.bruntwork.shop.features.cart

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseViewModelActivity
import com.riocallos.bruntwork.shop.databinding.ActivityCartBinding
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.features.checkout.CheckoutActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class CartActivity : BaseViewModelActivity<ActivityCartBinding, CartViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_cart

    private val cartAdapter by lazy {
        CartAdapter(this)
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

    private fun setupViews() {
        binding.toolbar.back.setOnClickListener {
            onBackPressed()
        }

        cartAdapter.onActionListener = object : CartAdapter.OnActionListener {
            override fun delete(position: Int, product: Product) {
                viewModel.delete(position, product)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView
            .apply {
                this.layoutManager = layoutManager
                post {
                    this.adapter = cartAdapter
                }
            }

        binding.buy.setOnClickListener {
            startActivity(Intent(this@CartActivity, CheckoutActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@CartActivity).toBundle())
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
        viewModel.getCart()
    }

    private fun handleState(state: CartState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            is CartState.ShowCartQuantity -> {
                binding.toolbar.cartQuantity.text = state.quantity
                binding.toolbar.cartQuantity.visibility = View.VISIBLE
            }
            CartState.HideCartQuantity -> {
                binding.toolbar.cartQuantity.visibility = View.GONE
            }
            is CartState.ShowCart -> {
                showCart(state.cart)
            }
            is CartState.ShowTotal -> {
                binding.total.text = state.total
            }
        }
    }

    private fun showCart(cart: List<Product>) {
        if(cart.isNotEmpty()) {
            binding.empty.visibility = View.GONE
            binding.recyclerView.visibility =  View.VISIBLE
            cartAdapter.submitList(cart)
            cartAdapter.notifyDataSetChanged()
        } else {
            binding.empty.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.totalContainer.visibility = View.GONE
        }
    }

}