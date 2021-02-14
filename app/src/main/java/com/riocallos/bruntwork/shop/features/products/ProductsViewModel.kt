package com.riocallos.bruntwork.shop.features.products

import android.os.Bundle
import com.riocallos.bruntwork.shop.base.BaseViewModel
import com.riocallos.bruntwork.shop.domain.models.Product
import com.riocallos.bruntwork.shop.repositories.products.ProductsRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class ProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<ProductsState> by lazy {
        PublishSubject.create<ProductsState>()
    }
    val state: Observable<ProductsState> = _state

    var category = "All"
    var allProducts = mutableListOf<Product>()
    var products = mutableListOf<Product>()

    fun getCartQuantity() = productsRepository
        .getCartQuantity()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .subscribeBy {
            if(it > 0) {
                _state.onNext(ProductsState.ShowCartQuantity(if (it <= 99) it.toString() else "99+"))
            } else {
                _state.onNext(ProductsState.HideCartQuantity)
            }
        }

    fun isCategorySelected(category: String) = this.category == category

    fun selectCategory(category: String) {
        if(this.category != category) {
            this.category = category
            products.clear()
            if(this.category == "All") {
              products.addAll(allProducts)
            } else {
                for (product in allProducts) {
                    if (this.category.contains(product.category)) {
                        products.add(product)
                    }
                }
            }
            _state.onNext(ProductsState.ShowCategories)
            _state.onNext(ProductsState.ShowProducts(products))
        }
    }

    fun localGetAllProducts() = productsRepository
        .localGetAllProducts()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSuccess {
            _state.onNext(ProductsState.ShowCategories)
        }
        .doOnError {
            _state.onNext(ProductsState.ShowProducts(listOf()))
        }
        .subscribeBy {
            it?.let {
                if(it.isEmpty()) {
                    remoteGetAllProducts()
                } else {
                    allProducts.clear()
                    allProducts.addAll(it)
                    products.clear()
                    products.addAll(it)
                    _state.onNext(ProductsState.ShowProducts(products))
                }
            }

        }

    private fun remoteGetAllProducts() = productsRepository
        .remoteGetAllProducts()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSuccess {
            _state.onNext(ProductsState.ShowCategories)
        }
        .doOnError {
            _state.onNext(ProductsState.ShowProducts(listOf()))
        }
        .subscribeBy {
            allProducts.clear()
            allProducts.addAll(it)
            products.clear()
            products.addAll(it)
            _state.onNext(ProductsState.ShowProducts(products))
        }

    fun addToCart(position: Int, product: Product) {
        val quantity = products[position].quantity + 1
        products[position].quantity = quantity
        productsRepository.updateCart(product.id, quantity)
        _state.onNext(ProductsState.UpdateProduct(position))
        getCartQuantity()
    }

    fun removeFromCart(position: Int, product: Product) {
        val quantity = products[position].quantity - 1
        products[position].quantity = quantity
        productsRepository.updateCart(product.id, quantity)
        _state.onNext(ProductsState.UpdateProduct(position))
        getCartQuantity()
    }

}