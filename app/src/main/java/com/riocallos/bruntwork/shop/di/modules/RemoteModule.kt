package com.riocallos.bruntwork.shop.di.modules

import android.app.Application
import com.riocallos.bruntwork.shop.database.AppDatabase
import com.riocallos.bruntwork.shop.di.providers.AssetProvider
import com.riocallos.bruntwork.shop.remote.products.ProductsRemoteSource
import com.riocallos.bruntwork.shop.remote.products.ProductsRemoteSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideAssetProvider(application: Application) = AssetProvider(application)

    @Provides
    @Singleton
    fun provideProductsRemoteSource(
        assetProvider: AssetProvider,
        appDatabase: AppDatabase
    ): ProductsRemoteSource = ProductsRemoteSourceImpl(assetProvider, appDatabase)

}