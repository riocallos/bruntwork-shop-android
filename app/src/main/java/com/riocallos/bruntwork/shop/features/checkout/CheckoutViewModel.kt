package com.riocallos.bruntwork.shop.features.checkout

import android.os.Bundle
import android.util.Patterns
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.riocallos.bruntwork.shop.base.BaseViewModel
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CheckoutViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<CheckoutState> by lazy {
        PublishSubject.create<CheckoutState>()
    }
    val state: Observable<CheckoutState> = _state

    fun getCartQuantity() = productsRepository
        .getCartQuantity()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            if(it > 0) {
                _state.onNext(CheckoutState.ShowCartQuantity(if (it <= 99) it.toString() else "99+"))
            } else {
                _state.onNext(CheckoutState.HideCartQuantity)
            }
        }

    fun getTotal() = productsRepository
        .getCart()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            it?.let {
                var total = 0.0
                for(product in it) {
                    total += (product.price.toDouble() * product.quantity)
                }
                _state.onNext(CheckoutState.ShowTotal("Pay $${String.format("%.0f", total)}"))
            }
        }

    fun checkout(name: String, email: String, agree: Boolean) {

        var valid = true

        if(name.isEmpty()) {
            valid = false
            _state.onNext(CheckoutState.ShowErrorName("Name is required"))
        } else {
            _state.onNext(CheckoutState.ShowErrorName(""))
        }

        if(email.isEmpty()) {
            valid = false
            _state.onNext(CheckoutState.ShowErrorEmail("Email is required"))
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false
            _state.onNext(CheckoutState.ShowErrorEmail("Email is invalid"))
        } else {
            _state.onNext(CheckoutState.ShowErrorEmail(""))
        }

        if(valid && !agree) {
            valid = false
            _state.onNext(CheckoutState.ShowError("You need to agree with the terms and conditions"))
        }

        if(valid) {
           getOrder()
        }

    }

    private fun getOrder() = productsRepository
        .getCart()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            it?.let {
                productsRepository.clearCart()
                getCartQuantity()
                val order = JsonObject();
                val products = JsonArray();
                for(product in it) {
                    products.add(Gson().toJsonTree(product, Product::class.java))
                }
                order.add("products", products)
                val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH)
                val id = sdf.format(Date())
                _state.onNext(CheckoutState.SaveOrder(id, order.toString().replace("\\",""), "order_$id.json"))
            }
        }

}