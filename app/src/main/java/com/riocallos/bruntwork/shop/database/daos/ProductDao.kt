package com.riocallos.bruntwork.shop.database.daos

import androidx.room.*
import com.riocallos.bruntwork.shop.domain.models.Product
import io.reactivex.Single

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Single<List<Product>>

    @Query("SELECT * FROM products WHERE id IN (:ids)")
    fun getAllByIds(ids: Array<String>): Single<List<Product>>

    @Query("SELECT EXISTS(SELECT * FROM products)")
    fun has(): Boolean

    @Query("SELECT * FROM products WHERE quantity > 0")
    fun getCart(): Single<List<Product>>

    @Query("SELECT SUM(quantity) FROM products")
    fun getCartQuantity(): Single<Int>

    @Query("UPDATE products SET quantity = :quantity WHERE id = :id")
    fun updateQuantity(id: String, quantity: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM products")
    fun deleteAll()
}