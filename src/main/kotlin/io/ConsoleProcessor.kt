package org.demotdd.io

fun writeToConsole(message: String) {
    println(message)
}

fun readFromConsole(): String {
    return readlnOrNull() ?: ""
}