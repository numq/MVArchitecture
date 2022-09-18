package com.numq.mvarchitecture.image

import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RemoveFavoriteTest {

    @MockK
    private lateinit var repository: ImageRepository
    private lateinit var removeFavorite: RemoveFavorite

    @Before
    fun init() {
        MockKAnnotations.init(this)
        removeFavorite = RemoveFavorite(repository)
    }

    @Test
    fun `should remove image to favorites and return updated`() {
        val input = emptyImage
        every { repository.removeFavorite(input) } returns input.copy(isFavorite = false).right()
        removeFavorite.invoke(input, onException = {
            assertIs<Exception>(it)
        }, onResult = {
            assertIs<Image>(it)
            assertEquals(input.copy(isFavorite = false), it)
        })
    }

}