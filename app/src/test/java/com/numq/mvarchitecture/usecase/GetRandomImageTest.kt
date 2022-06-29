package com.numq.mvarchitecture.usecase

import arrow.core.Either
import arrow.core.right
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

class GetRandomImageTest {

    private val stub: (Any) -> Any = {}

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
        getRandomImage.invoke(size) {
            it.fold(stub) { output ->
                assertIs<Either<Exception, Image>>(output)
                assertEquals(input, output)
            }
        }
    }

}