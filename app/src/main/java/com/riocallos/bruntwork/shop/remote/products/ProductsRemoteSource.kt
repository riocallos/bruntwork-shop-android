package com.riocallos.bruntwork.shop.remote.products

import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single

interface ProductsRemoteSource {
    fun getAllProducts(): Single<List<Product>>
}