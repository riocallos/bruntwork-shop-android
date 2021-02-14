package com.riocallos.bruntwork.shop.repositories.products

import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single

interface ProductsRepository {

    fun getCartQuantity(): Single<Int>

    fun getCart(): Single<List<Product>>

    fun updateCart(id: String, quantity: Int)

    fun clearCart()

    fun localGetAllProducts(): Single<List<Product>>

    fun remoteGetAllProducts(): Single<List<Product>>

}