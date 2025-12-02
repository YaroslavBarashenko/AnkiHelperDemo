package org.demotdd.util

enum class OS {
    WINDOWS,
    LINUX,
    MAC,
    OTHER
}

fun defineOs(osName: String): OS {
    val os = osName.lowercase()
    return when {
        os.contains("win") -> OS.WINDOWS
        os.contains("mac") -> OS.MAC
        os.contains("nix") -> OS.LINUX
        else -> OS.OTHER
    }
}
