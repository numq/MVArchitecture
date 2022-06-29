package com.numq.mvarchitecture.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.numq.mvarchitecture.data.image.ImageDao
import com.numq.mvarchitecture.domain.entity.Image

@Database(entities = [Image::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}