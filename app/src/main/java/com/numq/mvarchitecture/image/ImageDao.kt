package com.numq.mvarchitecture.image

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.numq.mvarchitecture.database.Database

@Dao
interface ImageDao {

    @Insert
    fun insert(image: Image): Long

    @Query("SELECT * FROM ${Database.FAVORITES_TABLE} WHERE id=:id")
    fun getById(id: String): Image?

    @Query("SELECT * FROM ${Database.FAVORITES_TABLE} ORDER BY added_at LIMIT :limit OFFSET :skip")
    fun getAll(skip: Int, limit: Int): List<Image>

    @Query("DELETE FROM ${Database.FAVORITES_TABLE} WHERE id=:id")
    fun delete(id: String): Int

    @Transaction
    fun ensureInsert(image: Image): Image? {
        val data =
            if (image.addedAt > 0) image else image.copy(addedAt = System.currentTimeMillis())
        return if (insert(data) > 0) image else null
    }

    @Transaction
    fun ensureDelete(image: Image) = if (delete(image.id) > 0) image else null

}