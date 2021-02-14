package com.riocallos.bruntwork.shop.di.modules

import android.app.Application
import com.riocallos.bruntwork.shop.database.AppDatabase
import com.riocallos.bruntwork.shop.local.products.ProductsLocalSource
import com.riocallos.bruntwork.shop.local.products.ProductsLocalSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application): AppDatabase {
        return AppDatabase.buildDatabase(application.baseContext)
    }

    @Provides
    @Singleton
    fun provideProductsLocalSource(
        appDatabase: AppDatabase
    ): ProductsLocalSource = ProductsLocalSourceImpl(appDatabase)

}