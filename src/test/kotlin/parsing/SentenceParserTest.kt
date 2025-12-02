package parsing

import io.kotest.matchers.shouldBe
import org.demotdd.parsing.getTranslationList
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

/*
todo
видалити послідовні пробіли та зайві лапки
*/
class SentenceParserTest {

    @Test
    fun `duplicated sentences removed from the list for translation`() {
        val inputText = """Hello, world!
            |Hello, world!
        """.trimMargin()


        val result = getTranslationList(inputText)

        result shouldBe listOf("Hello, world!")

    }

    @ParameterizedTest
    @ValueSource(strings = [" Hello, world! ", "    Hello, world!  ", "\nHello, world!\n", "* Hello, world!*"])
    fun `redundant characters are removed from input sentences`(inputText: String) {
        getTranslationList(inputText) shouldBe listOf("Hello, world!")
    }

    @Test
    fun `split sentences joined to complete considering punctuation marks`() {
        val inputText = "Normally the bridge is closed to the public, but there will be a special tour this\n" +
                "afternoon."

        getTranslationList(inputText) shouldBe listOf("Normally the bridge is closed to the public, but there will be a special tour this afternoon.")
    }

    @Test
    fun `real life input file successfully parsed`() {
        val inputFileContent =
            this::class.java.getResource("/input.txt")?.readText() ?: throw Exception("Input file hasn't been read.")

        val result = getTranslationList(inputFileContent)
        result.size shouldBe 11
        result.last() shouldBe "Normally the bridge is closed to the public, but there will be a special tour this afternoon."
        result.first() shouldBe "Wynona has suffered from cancer for years, so she decided to add a “do not resuscitate” order to her medical records."
    }
}