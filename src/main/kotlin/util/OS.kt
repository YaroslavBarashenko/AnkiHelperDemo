package org.demotdd.util

enum class OS {
    WINDOWS,
    LINUX,
    MAC,
    OTHER
}

fun defineOs(osName: String): OS = when {
    osName.contains("win") -> OS.WINDOWS
    osName.contains("mac") -> OS.MAC
    osName.contains("nix") -> OS.LINUX
    else -> OS.OTHER
}
