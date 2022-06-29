package com.numq.mvarchitecture.data.image

import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.numq.mvarchitecture.data.Database
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.emptyImage
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@SmallTest
class ImageDaoTest {

    private lateinit var database: Database

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            Database::class.java
        ).build()
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    fun shouldInsertAndReturnLong() {
        val input = emptyImage
        val output = database.imageDao().insert(input)
        assertEquals(output, 1L)
    }

    @Test
    fun shouldReturnImageById() {
        val input = emptyImage
        database.imageDao().insert(input)
        val output = database.imageDao().getById(input.id)
        assertEquals(input, output)
    }

    @Test
    fun shouldReturnAllImages() {
        val input = arrayOfNulls<Image>(10)
            .mapIndexed { idx, _ -> emptyImage.copy(id = idx.toString()) }
        input.forEach {
            database.imageDao().insert(it)
        }
        val output = database.imageDao().getAll(0, 10)
        assertEquals(input, output)
    }

    @Test
    fun shouldDeleteAndReturnCount() {
        val input = emptyImage
        database.imageDao().insert(input)
        val output = database.imageDao().delete(input.id)
        assertEquals(output, 1)
    }

    @Test
    fun shouldCompleteInsertReturnTransaction() {
        val input = emptyImage
        val output = database.imageDao().ensureInsert(input)
        assertEquals(input, output)
    }

    @Test
    fun shouldCompleteDeleteReturnTransaction() {
        val input = emptyImage
        database.imageDao().insert(input)
        val output = database.imageDao().ensureDelete(input)
        assertEquals(input, output)
    }

}