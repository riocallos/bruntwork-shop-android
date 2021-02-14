package com.riocallos.bruntwork.shop.features.order

sealed class OrderState {

    data class ShowCartQuantity(val quantity: String) : OrderState()

    object HideCartQuantity : OrderState()

}