package org.demotdd.translation

import com.deepl.api.Translator as DeeplApiTranslator

// Використовуйте цей клас для операцій з перекладом та отримання інформації про використання сервісу перекладу
class Translator(apiKey: String) {
    private val externalTranslator = DeeplApiTranslator(apiKey)
/*
* Available languages are:
* {SOURCE=[bg, cs, da, de, el, en, es, et, fi, fr, hu, id, it, ja, ko, lt, lv, nb, nl, pl, pt, ro, ru, sk, sl, sv, tr, uk, zh],
* TARGET=[bg, cs, da, de, el, en-GB, en-US, es, et, fi, fr, hu, id, it, ja, ko, lt, lv, nb, nl, pl, pt-BR, pt-PT, ro, ru, sk, sl, sv, tr, uk, zh, zh-HANS]}
*/
    fun translateLines(lines: List<String>, sourceLang: String, targetLang: String): List<String> {
        return externalTranslator.translateText(lines, sourceLang, targetLang).map { it.text }
    }

    val charactersLimit: Long? by lazy { externalTranslator.usage.character?.limit }

    val usedCharacters get() = externalTranslator.usage.character?.count

    fun getStatistics(): Triple<Long?, Long?, Long?> {
        val charactersLimit = charactersLimit
        val usedCharacters = usedCharacters
        val charactersReminder = charactersLimit?.minus(usedCharacters ?: 0)

        return Triple(charactersLimit, usedCharacters, charactersReminder)
    }
}