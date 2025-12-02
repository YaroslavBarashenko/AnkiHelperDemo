package util

import io.kotest.matchers.shouldBe
import org.demotdd.util.OS
import org.demotdd.util.defineOs
import kotlin.test.Test

class OSKtTest {

    @Test
    fun `Windows OS type correctly defined for Windows 10`() {
        defineOs("Windows 10") shouldBe OS.WINDOWS
    }
}