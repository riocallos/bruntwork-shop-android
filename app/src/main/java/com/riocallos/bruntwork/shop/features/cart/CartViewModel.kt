package com.riocallos.bruntwork.shop.features.cart

import android.os.Bundle
import android.util.Patterns
import com.riocallos.bruntwork.shop.base.BaseViewModel
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.features.products.ProductsState
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<CartState> by lazy {
        PublishSubject.create<CartState>()
    }
    val state: Observable<CartState> = _state

    var cart = mutableListOf<Product>()

    fun getCartQuantity() = productsRepository
        .getCartQuantity()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            if(it > 0) {
                _state.onNext(CartState.ShowCartQuantity(if (it <= 99) it.toString() else "99+"))
            } else {
                _state.onNext(CartState.HideCartQuantity)
            }
        }

    fun getCart() = productsRepository
        .getCart()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSuccess {
            var total = 0.0
            for(product in it) {
                total += (product.price.toDouble() * product.quantity)
            }
            _state.onNext(CartState.ShowTotal("$${String.format("%.0f", total)}"))
        }
        .doOnError {
            _state.onNext(CartState.ShowCart(listOf()))
        }
        .subscribeBy {
            it?.let {
                cart.clear()
                cart.addAll(it)
                _state.onNext(CartState.ShowCart(cart))
            }
        }

    fun delete(position: Int, product: Product) {
        cart[position].quantity = 0
        productsRepository.updateCart(product.id, 0)
        getCartQuantity()
        getCart()
    }

}