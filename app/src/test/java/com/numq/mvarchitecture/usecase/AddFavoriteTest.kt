package com.numq.mvarchitecture.usecase

import arrow.core.Either
import arrow.core.right
import com.numq.mvarchitecture.domain.entity.Image
import com.numq.mvarchitecture.domain.repository.ImageRepository
import com.numq.mvarchitecture.emptyImage
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AddFavoriteTest {

    private val stub: (Any) -> Any = {}

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
        addFavorite.invoke(input) {
            it.fold(stub) { output ->
                assertIs<Either<Exception, Image>>(output)
                assertEquals(input.copy(isFavorite = true), output)
            }
        }
    }
}