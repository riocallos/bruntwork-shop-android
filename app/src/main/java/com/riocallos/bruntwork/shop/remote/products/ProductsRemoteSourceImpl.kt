package com.riocallos.bruntwork.shop.remote.products

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.riocallos.bruntwork.shop.database.AppDatabase
import com.riocallos.bruntwork.shop.di.providers.AssetProvider
import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class ProductsRemoteSourceImpl @Inject constructor(
    private val assetProvider: AssetProvider,
    private val appDatabase: AppDatabase
) : ProductsRemoteSource {

    override fun getAllProducts(): Single<List<Product>> {

        var products: Single<List<Product>> = Single.just(listOf())

        try {
            val inputStream = assetProvider.getProducts()
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, StandardCharsets.UTF_8)
            val productsObject = JSONObject(json)
            val productsList = Gson().fromJson<List<Product>>(productsObject.getJSONArray("products").toString(), object: TypeToken<List<Product>>() {}.type)
            appDatabase.productDao().insertAll(*productsList.toTypedArray())
            products = Single.just(productsList)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return products

    }
}