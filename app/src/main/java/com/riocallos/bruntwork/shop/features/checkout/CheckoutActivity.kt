package com.riocallos.bruntwork.shop.features.checkout

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TypefaceSpan
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import com.riocallos.bruntwork.shop.R
import com.riocallos.bruntwork.shop.base.BaseViewModelActivity
import com.riocallos.bruntwork.shop.databinding.ActivityCheckoutBinding
import com.riocallos.bruntwork.shop.features.order.OrderActivity
import com.riocallos.bruntwork.shop.features.views.CustomTypefaceSpan
import io.reactivex.BackpressureStrategy
import io.reactivex.rxkotlin.subscribeBy
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class CheckoutActivity : BaseViewModelActivity<ActivityCheckoutBinding, CheckoutViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_checkout

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

        val typefaceSpan: TypefaceSpan = CustomTypefaceSpan(
            ResourcesCompat.getFont(
                this,
                R.font.montserrat_medium
            )!!
        )
        var spannableString = SpannableString(getString(R.string.name_hint))

        spannableString.setSpan(
            typefaceSpan,
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.name.hint = spannableString


        spannableString = SpannableString(getString(R.string.email_hint))

        spannableString.setSpan(
            typefaceSpan,
            0,
            spannableString.length,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.email.hint = spannableString

        binding.pay.setOnClickListener {
            viewModel.checkout(
                binding.name.text.toString(),
                binding.email.text.toString(),
                binding.agree.isChecked
            )
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
        viewModel.getTotal()
    }

    private fun handleState(state: CheckoutState) {
        if (lifecycle.currentState != Lifecycle.State.RESUMED) return
        when (state) {
            is CheckoutState.ShowCartQuantity -> {
                binding.toolbar.cartQuantity.text = state.quantity
                binding.toolbar.cartQuantity.visibility = View.VISIBLE
            }
            CheckoutState.HideCartQuantity -> {
                binding.toolbar.cartQuantity.visibility = View.GONE
            }
            is CheckoutState.ShowTotal -> {
                binding.pay.text = state.total
            }
            is CheckoutState.ShowErrorName -> {
                if (state.error.isNotEmpty()) {
                    binding.nameError.text = state.error
                    binding.nameError.visibility = View.VISIBLE
                } else {
                    binding.nameError.visibility = View.GONE
                }
            }
            is CheckoutState.ShowErrorEmail -> {
                if (state.error.isNotEmpty()) {
                    binding.emailError.text = state.error
                    binding.emailError.visibility = View.VISIBLE
                } else {
                    binding.emailError.visibility = View.GONE
                }
            }
            is CheckoutState.ShowError -> {
                Toast.makeText(this, state.error, Toast.LENGTH_SHORT).show()
            }
            is CheckoutState.SaveOrder -> {
                val file = File(filesDir, state.filename)
                val fileWriter = FileWriter(file)
                val bufferedWriter = BufferedWriter(fileWriter)
                bufferedWriter.write(state.order)
                bufferedWriter.close()
                val intent = Intent(this@CheckoutActivity, OrderActivity::class.java)
                intent.putExtra("order", state.id)
                startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(this@CheckoutActivity).toBundle()
                )
            }
        }
    }

}