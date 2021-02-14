package com.riocallos.bruntwork.shop.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riocallos.bruntwork.shop.di.ViewModelFactory
import com.riocallos.bruntwork.shop.di.scopes.ViewModelKey
import com.riocallos.bruntwork.shop.features.cart.CartViewModel
import com.riocallos.bruntwork.shop.features.checkout.CheckoutViewModel
import com.riocallos.bruntwork.shop.features.order.OrderViewModel
import com.riocallos.bruntwork.shop.features.products.ProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    abstract fun bindProductsViewModel(viewModel: ProductsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindCartViewModel(viewModel: CartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckoutViewModel::class)
    abstract fun bindCheckoutViewModel(viewModel: CheckoutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindOrderViewModel(viewModel: OrderViewModel): ViewModel

}