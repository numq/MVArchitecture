package com.numq.mvarchitecture.usecase

import arrow.core.Either
import arrow.core.right
import com.numq.mvarchitecture.domain.repository.ImageRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CheckFavoriteTest {

    private val stub: (Any) -> Any = {}

    @MockK
    private lateinit var repository: ImageRepository
    private lateinit var checkFavorite: CheckFavorite

    @Before
    fun init() {
        MockKAnnotations.init(this)
        checkFavorite = CheckFavorite(repository)
    }

    @Test
    fun `should check is image in favorites and return state`() {
        every { repository.checkFavorite("0") } returns true.right()
        checkFavorite.invoke("0") {
            it.fold(stub) { output ->
                assertIs<Either<Exception, Boolean>>(output)
                assertEquals(true, output)
            }
        }
    }

}