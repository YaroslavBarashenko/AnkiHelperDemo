package org.demotdd.validations

import org.demotdd.org.demotdd.other.OS
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists

class InvalidPathException(message: String) : Exception(message)

fun validateInputPath(inputPath: Path) {
    if (!inputPath.exists()) {
        throw InvalidPathException("Input file doesn't exists on specified path")
    }

    if (Files.size(inputPath) == 0L) {
        throw InvalidPathException("Input file is empty")
    }
}

val blacklistedWindowsFolders =
    listOf("C:\\Windows", "C:\\Program Files", "C:\\Program Files (x86)", "C:\\ProgramData", "C:\\AppData")
val blacklistedLinuxFolders =
    listOf("/boot", "/bin", "/dev", "/etc", "/lib", "/proc", "/run", "/sbin", "/sys", "/usr", "/tmp")

val forbiddenFolderByOs = mapOf(
    OS.WINDOWS to blacklistedWindowsFolders,
    OS.LINUX to blacklistedLinuxFolders,
    OS.MAC to emptyList(),
    OS.OTHER to emptyList()
)

fun validateOutputPath(outputFilePath: Path, os: OS) {
    val folders = forbiddenFolderByOs[os]
    if (folders?.any { outputFilePath.startsWith(it) } == true) {
        throw InvalidPathException("Output file should not be located in any of the blacklisted folders: $folders")
    }
}

