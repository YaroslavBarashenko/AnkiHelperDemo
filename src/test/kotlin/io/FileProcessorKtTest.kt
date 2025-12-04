package io

import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.demotdd.io.cleanFile
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText
import kotlin.test.Test

class FileProcessorKtTest {
    @Test
    fun `file clean performs successfully`(@TempDir inputPath: Path) {
        val input = inputPath.resolve("input.txt")
        input.writeText("Some text")
        Files.size(input) shouldBeGreaterThan 0

        cleanFile(input)

        Files.size(input) shouldBe 0
    }
}