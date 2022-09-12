package com.numq.mvarchitecture.usecase

import arrow.core.Either
import arrow.core.right
import com.numq.mvarchitecture.image.Image
import com.numq.mvarchitecture.image.ImageRepository
import com.numq.mvarchitecture.image.RemoveFavorite
import com.numq.mvarchitecture.utility.emptyImage
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class RemoveFavoriteTest {

    private val stub: (Any) -> Any = {}

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
        removeFavorite.invoke(input) {
            it.fold(stub) { output ->
                assertIs<Either<Exception, Image>>(output)
                assertEquals(input.copy(isFavorite = false), output)
            }
        }
    }

}