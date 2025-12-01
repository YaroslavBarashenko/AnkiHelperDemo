package parsing

import io.kotest.matchers.shouldBe
import org.demotdd.parsing.getTranslationList
import kotlin.test.Test

/*
todo
 розпарсити вхідний текст на речення для перекладу,
  зайві пробіли, табуляції, нові рядки,
 дубльовані рядки. +
Приклад вхідного тексту: у файлі /src/test/resources/input.txt
Можуть бути кілька речень в одиниці перекладу, може бути кілька великих літер в одній одиниці, може бути зірочка а може не бути.
Ознака закінчення одиниці тексту - наявність знака пунктуації: крапка, знак питання або знак оклику. Може також супроводжуватися лапками.
Якщо закінчується лапками то і починається лапками, у цьому випадку зовнішні лапки не повинні відправлятись на переклад і потрапляти у вихідний файл.
*/
class SentenceParserTest {

    @Test
    fun `duplicated sentences removed from the list for translation`() {
        val inputText = """Hello, world!
            |Hello, world!
        """.trimMargin()


        val result = getTranslationList(inputText)

        result shouldBe listOf("Hello, world!")

    }


}