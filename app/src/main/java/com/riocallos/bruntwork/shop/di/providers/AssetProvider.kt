package com.riocallos.bruntwork.shop.di.providers

import android.app.Application
import android.content.Context
import com.riocallos.bruntwork.shop.di.scopes.ApplicationContext
import javax.inject.Inject

class AssetProvider @Inject constructor(
    private val application: Application
) {
    
    fun getProducts() = application.baseContext.assets.open("products.json")
}