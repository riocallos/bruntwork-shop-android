package com.riocallos.bruntwork.shop.repositories.products

import com.riocallos.bruntwork.shop.local.products.ProductsLocalSource
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.remote.products.ProductsRemoteSource
import io.reactivex.Single
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
        private val productsLocalSource: ProductsLocalSource,
        private val productsRemoteSource: ProductsRemoteSource
) : ProductsRepository {

    override fun getCartQuantity(): Single<Int> {
        return productsLocalSource.getCartQuantity()
    }

    override fun getCart(): Single<List<Product>> {
        return productsLocalSource.getCart()
    }

    override fun updateCart(id: String, quantity: Int) {
        productsLocalSource.updateCart(id, quantity)
    }

    override fun clearCart() {
        productsLocalSource.clearCart()
    }

    override fun localGetAllProducts(): Single<List<Product>> {
        return productsLocalSource.getAllProducts()
    }

    override fun remoteGetAllProducts(): Single<List<Product>> {
        return productsRemoteSource.getAllProducts()
    }

}