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

class GetFavoritesTest {

    private val stub: (Any) -> Any = {}

    @MockK
    private lateinit var repository: ImageRepository
    private lateinit var getFavorites: GetFavorites

    @Before
    fun init() {
        MockKAnnotations.init(this)
        getFavorites = GetFavorites(repository)
    }

    @Test
    fun `should return list of favorites`() {
        val input = arrayOfNulls<Image>(10).map { emptyImage }
        val (skip, limit) = Pair(0, 10)
        every { repository.getFavorites(skip, limit) } returns input.right()
        getFavorites.invoke(Pair(skip, limit)) {
            it.fold(stub) { output ->
                assertIs<Either<Exception, List<Image>>>(output)
                assertEquals(input, output)
            }
        }
    }

}