package com.numq.mvarchitecture.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.ImageDao

@Database(entities = [Image::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    companion object {
        const val NAME = "mvarchitecture"
        const val FAVORITES_TABLE = "favorites"
    }
}