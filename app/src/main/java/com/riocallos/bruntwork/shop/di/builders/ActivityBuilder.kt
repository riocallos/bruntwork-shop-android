package com.riocallos.bruntwork.shop.di.builders

import com.riocallos.bruntwork.shop.di.scopes.ActivityScope
import com.riocallos.bruntwork.shop.features.cart.CartActivity
import com.riocallos.bruntwork.shop.features.checkout.CheckoutActivity
import com.riocallos.bruntwork.shop.features.order.OrderActivity
import com.riocallos.bruntwork.shop.features.products.ProductsActivity
import com.riocallos.bruntwork.shop.features.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeProductsActivity(): ProductsActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCartActivity(): CartActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeCheckoutActivity(): CheckoutActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeOrderActivity(): OrderActivity

}