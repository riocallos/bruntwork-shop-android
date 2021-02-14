package com.riocallos.bruntwork.shop.di.modules

import com.riocallos.bruntwork.shop.local.products.ProductsLocalSource
import com.riocallos.bruntwork.shop.remote.products.ProductsRemoteSource
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepository
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesProductsRepository(
            productsLocalSource: ProductsLocalSource,
            productsRemoteSource: ProductsRemoteSource
    ): ProductsRepository = ProductsRepositoryImpl(productsLocalSource, productsRemoteSource)

}