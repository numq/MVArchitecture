package com.numq.mvarchitecture.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.numq.mvarchitecture.image.ImageDao
import com.numq.mvarchitecture.image.Image

@Database(entities = [Image::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}