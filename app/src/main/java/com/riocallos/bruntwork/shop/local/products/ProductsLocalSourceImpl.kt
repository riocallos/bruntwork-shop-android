package com.riocallos.bruntwork.shop.local.products

import com.riocallos.bruntwork.shop.database.AppDatabase
import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single
import javax.inject.Inject

class ProductsLocalSourceImpl @Inject constructor(
        private val appDatabase: AppDatabase
) : ProductsLocalSource {

    override fun getCartQuantity(): Single<Int> {
        return if(appDatabase.productDao().has()) {
            appDatabase.productDao().getCartQuantity()
        } else Single.just(0)
    }

    override fun getCart(): Single<List<Product>> {
        return appDatabase.productDao().getCart()
    }

    override fun updateCart(id: String, quantity: Int) {
        appDatabase.productDao().updateQuantity(id, quantity)
    }

    override fun clearCart() {
        appDatabase.productDao().deleteAll()
    }

    override fun getAllProducts(): Single<List<Product>> {
        return appDatabase.productDao().getAll()
    }

}