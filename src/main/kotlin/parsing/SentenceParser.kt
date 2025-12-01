package org.demotdd.parsing

/*todo показати повідомлення про помилку,
   якщо файл не може бути розпарсений.
   Наприклад: в кінці файлу немає знака пунктуації
*/

fun getTranslationList(inputText: String): List<String> {


    return inputText.lines().distinct()
}

