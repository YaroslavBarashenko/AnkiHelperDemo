package org.demotdd.mapping

fun joinLines(translated: List<String>, original: List<String>): String =
    translated.zip(original).joinToString("\n") { "${it.first}|${it.second}" }
