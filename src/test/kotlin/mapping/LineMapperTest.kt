package mapping

import io.kotest.matchers.shouldBe
import org.demotdd.mapping.joinLines
import org.junit.jupiter.api.Test

class LineMapperTest {
    @Test
    fun `translated and original strings are joined in ready to write csv item`() {
        val original = listOf("Hello, world!", "How are you?")
        val translated = listOf("Привіт, світе!", "Як ти?")
        joinLines(translated, original) shouldBe "Привіт, світе!|Hello, world!\nЯк ти?|How are you?"
    }

}