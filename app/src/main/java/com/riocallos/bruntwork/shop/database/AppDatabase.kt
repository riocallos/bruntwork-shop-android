package com.riocallos.bruntwork.shop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riocallos.bruntwork.shop.database.daos.ProductDao
import com.riocallos.bruntwork.shop.domain.models.Product

@Database(version = 1, exportSchema = false, entities = [Product::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java,  "${context.packageName.replace(".", "_")}.db")
            .allowMainThreadQueries()
            .build()
    }

    abstract fun productDao(): ProductDao

}
