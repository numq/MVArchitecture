package com.numq.mvarchitecture.image

import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AddFavoriteTest {

    @MockK
    private lateinit var repository: ImageRepository
    private lateinit var addFavorite: AddFavorite

    @Before
    fun init() {
        MockKAnnotations.init(this)
        addFavorite = AddFavorite(repository)
    }

    @Test
    fun `should add image to favorites and return updated`() {
        val input = emptyImage
        every { repository.addFavorite(input) } returns input.copy(isFavorite = true).right()
        addFavorite.invoke(input, onException = {
            assertIs<Exception>(it)
        }, onResult = {
            assertIs<Image>(it)
            assertEquals(input.copy(isFavorite = true), it)
        })
    }
}