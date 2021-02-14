package com.riocallos.bruntwork.shop.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product (
    @PrimaryKey var id: String = "",
    var name: String = "",
    var category: String = "",
    var price: String = "",
    var bgColor: String = "",
    var quantity: Int = 0
)