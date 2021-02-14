package com.riocallos.bruntwork.shop.features.cart

import com.riocallos.bruntwork.shop.domain.models.Product

sealed class CartState {

    data class ShowCartQuantity(val quantity: String) : CartState()

    object HideCartQuantity : CartState()

    data class ShowCart(val cart: List<Product>) : CartState()

    data class ShowTotal(val total: String) : CartState()

}