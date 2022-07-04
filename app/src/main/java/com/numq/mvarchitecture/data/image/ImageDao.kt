package com.numq.mvarchitecture.data.image

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.platform.constant.AppConstants

@Dao
interface ImageDao {

    @Insert
    fun insert(image: Image): Long

    @Query("SELECT * FROM ${AppConstants.Database.Images.TABLE_NAME} WHERE id=:id")
    fun getById(id: String): Image?

    @Query("SELECT * FROM ${AppConstants.Database.Images.TABLE_NAME} ORDER BY added_at LIMIT :limit OFFSET :skip")
    fun getAll(skip: Int, limit: Int): List<Image>

    @Query("DELETE FROM ${AppConstants.Database.Images.TABLE_NAME} WHERE id=:id")
    fun delete(id: String): Int

    @Transaction
    fun ensureInsert(image: Image): Image? {
        val data = if (image.addedAt > 0) image else image.copy(addedAt = System.currentTimeMillis())
        return if (insert(data) > 0) image else null
    }

    @Transaction
    fun ensureDelete(image: Image) = if (delete(image.id) > 0) image else null

}