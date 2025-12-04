package util

import io.kotest.matchers.shouldBe
import org.demotdd.util.OS
import org.demotdd.util.countSymbols
import org.demotdd.util.defineOs
import kotlin.test.Test

class HelperKtTest {

    @Test
    fun `Windows OS type correctly defined for Windows 10`() {
        defineOs("Windows 10") shouldBe OS.WINDOWS
    }

    @Test
    fun `symbols in prepared lines are counted correctly`() {
        val originalLines = listOf("Hello, world!", "How are you?")
        countSymbols(originalLines) shouldBe 25
    }

}