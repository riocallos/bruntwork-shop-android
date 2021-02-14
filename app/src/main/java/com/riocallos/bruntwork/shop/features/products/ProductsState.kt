package com.riocallos.bruntwork.shop.features.products

import com.riocallos.bruntwork.shop.domain.models.Product

sealed class ProductsState {

    object ShowCategories : ProductsState()

    data class ShowCartQuantity(val quantity: String) : ProductsState()

    object HideCartQuantity : ProductsState()

    data class ShowProducts(val products: List<Product>) : ProductsState()

    data class UpdateProduct(val position: Int) : ProductsState()

}