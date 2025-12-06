package parsing

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.demotdd.parsing.IllegalInputFileException
import org.demotdd.parsing.prepareLinesForTranslation
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test

class SentenceParserTest {

    @Test
    fun `duplicated sentences removed from the list for translation`() {
        val inputText = """Hello, world!
            |Hello, world!
        """.trimMargin()


        val result = prepareLinesForTranslation(inputText)

        result shouldBe listOf("Hello, world!")

    }

    @ParameterizedTest
    @ValueSource(strings = [" Hello, world! ", "    Hello, world!  ", "\nHello, world!\n", "* Hello, world!*", "Hello,   world!", "“Hello, world!”"])
    fun `redundant characters are removed from input sentences`(inputText: String) {
        prepareLinesForTranslation(inputText) shouldBe listOf("Hello, world!")
    }

    @Test
    fun `program stops with exception in case empty input string`() {
        val exception =
            assertThrowsExactly(IllegalInputFileException::class.java) { prepareLinesForTranslation("") }

        exception.message shouldBe "Empty file can't be parsed"
    }

    @Test
    fun `completed quotation marks persists in text`() {
        val input =
            "Wynona has suffered from cancer for years, so she decided to add a “do not resuscitate” order to her medical records."

        val result = prepareLinesForTranslation(input)

        result.size shouldBe 1
        result.first() shouldBe input
    }

    @Test
    fun `partial quotation marks are removed from text`() {
        val input =
            """Hello, “world!"""

        val result = prepareLinesForTranslation(input)

        result.size shouldBe 1
        result.first() shouldBe "Hello, world!"
    }

    @Test
    fun `split sentences joined to complete considering punctuation marks`() {
        val inputText = "Normally the bridge is closed to the public, but there will be a special tour this\n" +
                "afternoon."

        prepareLinesForTranslation(inputText) shouldBe listOf("Normally the bridge is closed to the public, but there will be a special tour this afternoon.")
    }

    @Test
    fun `input file with tabs and quotation marks successfully parsed`() {
        val inputFileContent =
            this::class.java.getResource("/input.txt")?.readText() ?: throw Exception("Input file hasn't been read.")

        val result = prepareLinesForTranslation(inputFileContent)
        result.size shouldBe 11

        result.last() shouldBe "Normally the bridge is closed to the public, but there will be a special tour this afternoon."
        result.first() shouldBe "Wynona has suffered from cancer for years, so she decided to add a “do not resuscitate” order to her medical records."

        result shouldContainExactlyInAnyOrder listOf(
            "Normally the bridge is closed to the public, but there will be a special tour this afternoon.",
            "Everyone began waving as the boat set sail.",
            "They rented a cabin in the woods where they could spend all day in nature.",
            "Save your pay stubs so that at the end of the year, you can see how much money you’ve contributed to your retirement funds.",
            "If you don’t start paying alimony to your ex-husband, the court might decide to garnish your wages.",
            "How can you write with such a short pencil stub?",
            "Her wages barely cover rent and food.",
            "She needs to find a higher-paying job.",
            "James thought he was experiencing cardiac arrest, but he was just having stomach pains from all of the food he ate.",
            "B: Thank God for small favors!",
            "Wynona has suffered from cancer for years, so she decided to add a “do not resuscitate” order to her medical records."
        )
    }
}
