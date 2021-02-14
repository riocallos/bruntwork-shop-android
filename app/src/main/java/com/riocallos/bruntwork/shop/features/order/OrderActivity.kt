package com.riocallos.bruntwork.shop.features.order

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseViewModelActivity
import com.riocallos.bruntwork.shop.databinding.ActivityOrderBinding
import com.riocallos.bruntwork.shop.features.products.ProductsActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy

class OrderActivity : BaseViewModelActivity<ActivityOrderBinding, OrderViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_order

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

        val bundle = intent.extras
        bundle?.let {
            binding.orderId.text = "#${it.getString("order")}"
        }

        binding.products.setOnClickListener {
            startActivity(Intent(this@OrderActivity, ProductsActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@OrderActivity).toBundle())
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
    }

    private fun handleState(state: OrderState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            is OrderState.ShowCartQuantity -> {
                binding.toolbar.cartQuantity.text = state.quantity
                binding.toolbar.cartQuantity.visibility = View.VISIBLE
            }
            OrderState.HideCartQuantity -> {
                binding.toolbar.cartQuantity.visibility = View.GONE
            }
        }
    }

}