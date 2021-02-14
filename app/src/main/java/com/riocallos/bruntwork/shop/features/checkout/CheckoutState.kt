package com.riocallos.bruntwork.shop.features.checkout

import com.riocallos.bruntwork.shop.features.cart.CartState

sealed class CheckoutState {

    data class ShowCartQuantity(val quantity: String) : CheckoutState()

    object HideCartQuantity : CheckoutState()

    data class ShowTotal(val total: String) : CheckoutState()

    data class ShowErrorName(var error: String) : CheckoutState()

    data class ShowErrorEmail(var error: String) : CheckoutState()

    data class ShowError(var error: String) : CheckoutState()

    data class SaveOrder(var id: String, var order: String, var filename: String) : CheckoutState()

}