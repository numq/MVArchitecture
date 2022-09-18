package com.numq.mvarchitecture.image

import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetFavoritesTest {

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
        getFavorites.invoke(Pair(skip, limit), onException = {
            assertIs<Exception>(it)
        }, onResult = {
            assertIs<List<Image>>(it)
            assertEquals(input, it)
        })
    }

}