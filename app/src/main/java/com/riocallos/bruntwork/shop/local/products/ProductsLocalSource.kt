package com.riocallos.bruntwork.shop.local.products

import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single

interface ProductsLocalSource {
    fun getCartQuantity(): Single<Int>
    fun getCart(): Single<List<Product>>
    fun updateCart(id: String, quantity: Int)
    fun clearCart()
    fun getAllProducts(): Single<List<Product>>
}