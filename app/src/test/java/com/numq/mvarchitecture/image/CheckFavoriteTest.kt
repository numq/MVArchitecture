package com.numq.mvarchitecture.image

import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CheckFavoriteTest {

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
        checkFavorite.invoke("0", onException = {
            assertIs<Exception>(it)
        }, onResult = {
            assertIs<Boolean>(it)
            assertEquals(true, it)
        })
    }

}