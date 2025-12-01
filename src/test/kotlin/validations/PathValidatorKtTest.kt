package validations

import io.kotest.matchers.shouldBe
import org.demotdd.util.OS.LINUX
import org.demotdd.util.OS.WINDOWS
import org.demotdd.validations.InvalidPathException
import org.demotdd.validations.validateInputPath
import org.demotdd.validations.validateOutputPath
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.createFile

class PathValidatorKtTest {
    @Test
    fun `validation fails on path to non-existing file`(@TempDir tempDir: Path) {
        val nonExistingInputPath = tempDir.resolve("non-existing-folder")
        assertThrowsExactly(InvalidPathException::class.java) {
            validateInputPath(nonExistingInputPath)
        }
    }

    @Test
    fun `validation fails on path to empty input file`(@TempDir tempDir: Path) {
        val inputFilePath = tempDir.resolve("empty-input-file.txt").createFile()

        assertThrowsExactly(InvalidPathException::class.java) {
            validateInputPath(inputFilePath)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["C:\\Windows", "C:\\Program Files", "C:\\Program Files (x86)", "C:\\ProgramData", "C:\\AppData"])
    fun `output file can not be in Windows blacklist folders`(pathPrefix: String) {
        val outputFilePath = Paths.get(pathPrefix).resolve("input-file.txt")
        val throwable = assertThrowsExactly(InvalidPathException::class.java) {
            validateOutputPath(outputFilePath, WINDOWS)
        }

        throwable.message shouldBe "Output file should not be located in any of the blacklisted folders: [C:\\Windows, C:\\Program Files, C:\\Program Files (x86), C:\\ProgramData, C:\\AppData]"
    }

    @ParameterizedTest
    @ValueSource(strings = ["/boot", "/bin", "/dev", "/etc", "/lib", "/proc", "/run", "/sbin", "/sys", "/usr", "/tmp"])
    fun `output file can not be in Linux blacklist folders`(pathPrefix: String) {
        val outputFilePath = Paths.get(pathPrefix).resolve("input-file.txt")
        val throwable = assertThrowsExactly(InvalidPathException::class.java) {
            validateOutputPath(outputFilePath, LINUX)
        }

        throwable.message shouldBe "Output file should not be located in any of the blacklisted folders: [/boot, /bin, /dev, /etc, /lib, /proc, /run, /sbin, /sys, /usr, /tmp]"
    }

    @ParameterizedTest
    @ValueSource(strings = ["/home"])
    fun `output file can be outside Linux blacklist folders`(pathPrefix: String) {
        val outputFilePath = Paths.get(pathPrefix).resolve("input-file.txt")
        assertDoesNotThrow {
            validateOutputPath(outputFilePath, LINUX)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["C:\\Downloads"])
    fun `output file can be outside Windows blacklist folders`(pathPrefix: String) {
        val outputFilePath = Paths.get(pathPrefix).resolve("input-file.txt")
        assertDoesNotThrow {
            validateOutputPath(outputFilePath, WINDOWS)
        }
    }
}