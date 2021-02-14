package com.riocallos.bruntwork.shop.features.order

import android.os.Bundle
import com.riocallos.bruntwork.shop.base.BaseViewModel
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class OrderViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<OrderState> by lazy {
        PublishSubject.create<OrderState>()
    }
    val state: Observable<OrderState> = _state

    fun getCartQuantity() = productsRepository
        .getCartQuantity()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            if(it > 0) {
                _state.onNext(OrderState.ShowCartQuantity(if (it <= 99) it.toString() else "99+"))
            } else {
                _state.onNext(OrderState.HideCartQuantity)
            }
        }

}