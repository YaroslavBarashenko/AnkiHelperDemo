package org.demotdd.parsing

/*todo показати повідомлення про помилку,
   якщо файл не може бути розпарсений.
   Наприклад: в кінці файлу немає знака пунктуації
*/

val signs = setOf('?', '!', '.')
private fun Char.isPunctuationSign() = this in signs

class IllegalInputFileException(message: String) : Exception(message)

fun getTranslationList(inputText: String): List<String> {
    val input = inputText.replace(Regex("[*\\n\\r]+"), " ").trim()
    val lines = mutableListOf<String>()
    var endIndex = input.lastIndex
    var startIndex = endIndex - 1
    if (!input.last().isPunctuationSign()) {
        throw IllegalInputFileException("All sentences should end with one the symbols: $signs")
    }

    while (startIndex >= 0) {
        if (input[startIndex].isPunctuationSign()) {
            lines.add(input.substring(startIndex + 1, endIndex + 1))
            endIndex = startIndex
        }
        startIndex--
    }

    lines.add(input.substring(0, endIndex + 1))

    return lines.map { it.trim() }.filter { it.isNotBlank() }.distinct()
}

