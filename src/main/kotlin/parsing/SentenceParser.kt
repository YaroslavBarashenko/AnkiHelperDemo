package org.demotdd.parsing

/*todo показати повідомлення про помилку,
   якщо файл не може бути розпарсений.
   Наприклад: в кінці файлу немає знака пунктуації
*/

val marks = setOf('?', '!', '.')
private fun Char.isPunctuationMark() = this in marks

class IllegalInputFileException(message: String) : Exception(message)

fun getTranslationList(inputText: String): List<String> {
    val input = inputText.replace(Regex("[*\\n\\r]+"), " ").trim()
    val lines = mutableListOf<String>()
    var endIndex = input.lastIndex
    var startIndex = endIndex - 1
    if (!input.last().isPunctuationMark()) {
        throw IllegalInputFileException("All sentences should end with one the symbols: $marks")
    }

    while (startIndex >= 0) {
        if (input[startIndex].isPunctuationMark()) {
            lines.add(input.substring(startIndex + 1, endIndex + 1))
            endIndex = startIndex
        }
        startIndex--
    }

    lines.add(input.substring(0, endIndex + 1))

    return lines.map { it.trim() }.filter { it.isNotBlank() }.distinct()
}

