package com.numq.mvarchitecture.image

import arrow.core.right
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class GetRandomImageTest {

    @MockK
    private lateinit var repository: ImageRepository
    private lateinit var getRandomImage: GetRandomImage

    @Before
    fun init() {
        MockKAnnotations.init(this)
        getRandomImage = GetRandomImage(repository)
    }

    @Test
    fun `should return random image`() {
        val size = ImageSize(0, 0)
        val input = emptyImage
        every { repository.getRandomImage(size) } returns input.right()
        getRandomImage.invoke(size, onException = {
            assertIs<Exception>(it)
        }, onResult = {
            assertIs<Image>(it)
            assertEquals(input, it)
        })
    }

}