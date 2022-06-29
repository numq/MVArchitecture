package com.numq.mvarchitecture.data.domain

import arrow.core.Either
import arrow.core.right
import com.numq.mvarchitecture.data.image.ImageApi
import com.numq.mvarchitecture.data.image.ImageDao
import com.numq.mvarchitecture.data.image.ImageData
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.entity.ImageSize
import com.numq.mvarchitecture.domain.repository.ImageRepository
import com.numq.mvarchitecture.emptyImage
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class ImageRepositoryTest {

    @MockK(relaxed = true)
    private lateinit var service: ImageApi

    @MockK
    private lateinit var dao: ImageDao

    private lateinit var repository: ImageRepository

    @Before
    fun before() {
        MockKAnnotations.init(this)
        repository = ImageData(service, dao)
    }

    @Test
    fun `should return image`() {
        val size = ImageSize(0, 0)

        val output = repository.getRandomImage(size)
        assertIs<Either<Exception, Image>>(output)
    }

    @Test
    fun `should return list of favorite images`() {
        val (skip, limit) = Pair(0, 10)
        val input = arrayOfNulls<Image>(10)
            .mapIndexed { idx, _ -> emptyImage.copy(id = idx.toString()) }
        every { dao.getAll(skip, limit) } returns input

        val outputList = repository.getFavorites(skip, limit)
        assertIs<Either<Exception, List<Image>>>(outputList)
        assertEquals(input.right(), outputList)
    }

    @Test
    fun `should add image to favorites`() {
        val input = emptyImage
        every { dao.ensureInsert(input) } returns input.copy(isFavorite = true)

        val outputImage = repository.addFavorite(input)
        assertIs<Either<Exception, Image>>(outputImage)
        assertEquals(input.copy(isFavorite = true).right(), outputImage)
    }

    @Test
    fun `should remove image from favorites`() {
        val input = emptyImage
        every { dao.ensureDelete(input) } returns input.copy(isFavorite = false)

        val outputImage = repository.removeFavorite(input)
        assertIs<Either<Exception, Image>>(outputImage)
        assertEquals(input.copy(isFavorite = false).right(), outputImage)
    }

    @Test
    fun `should check is image in favorites`() {
        val input = emptyImage
        every { dao.getById(input.id) } returns input

        val outputState = repository.checkFavorite(input.id)
        assertIs<Either<Exception, Boolean>>(outputState)
        assertEquals(true.right(), outputState)
    }

}
