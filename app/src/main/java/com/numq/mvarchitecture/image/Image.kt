package com.numq.mvarchitecture.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.numq.mvarchitecture.database.Database

@Entity(tableName = Database.FAVORITES_TABLE)
data class Image(
    @PrimaryKey
    @SerializedName("id") var id: String,
    @ColumnInfo(name = "author")
    @SerializedName("author") var author: String,
    @ColumnInfo(name = "width")
    @SerializedName("width") var width: Int,
    @ColumnInfo(name = "height")
    @SerializedName("height") var height: Int,
    @ColumnInfo(name = "url")
    @SerializedName("url") var url: String,
    @ColumnInfo(name = "download_url")
    @SerializedName("download_url") var downloadUrl: String,
    @ColumnInfo(name = "added_at")
    val addedAt: Long = 0L,
    var isFavorite: Boolean = false
)