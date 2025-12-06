package org.demotdd

import config.ConfigReader
import org.demotdd.OutputTreatment.APPEND
import org.demotdd.io.appendFile
import org.demotdd.io.cleanFile
import org.demotdd.io.readFromFile
import org.demotdd.io.writeToConsole
import org.demotdd.io.writeToFile
import org.demotdd.mapping.joinLines
import org.demotdd.parsing.prepareLinesForTranslation
import org.demotdd.translation.Translator
import org.demotdd.util.countSymbols
import org.demotdd.util.defineOs
import org.demotdd.validations.validateInputPath
import org.demotdd.validations.validateOutputPath
import java.nio.file.InvalidPathException
import java.nio.file.Paths
import kotlin.io.path.exists

enum class OutputTreatment(val flag: String) {
    APPEND("a"),
    OVERWRITE("o")
}

fun main(args: Array<String>) {
    require(args.size >= 2) { "Please provide input and output file paths as arguments." }

    try {
        val inputPath = Paths.get(args[0])
        val outputPath = Paths.get(args[1])
        val outputTreatment: String? = args.getOrNull(2)

        validateInputPath(inputPath)
        validateOutputPath(outputPath, defineOs(System.getProperty("os.name")))

        val configReader = ConfigReader()
        val apiKey = configReader.getApiKey() ?: throw Exception("Translation is not possible without API key")
        writeToConsole("Extracted API key")

        val inputText = readFromFile(inputPath)
        val originalLines = prepareLinesForTranslation(inputText)
        val symbolsCount = countSymbols(originalLines)
        writeToConsole("Parsed input file at $inputPath")

        val translator = Translator(apiKey)

        val (charactersLimit, usedCharacters, charactersReminder) = translator.getStatistics()
        writeToConsole(
            "Service usage statistics before translation: character limit: $charactersLimit, " +
                    "used: $usedCharacters, reminder $charactersReminder"
        )

        writeToConsole("Sending parsed lines to translation service... Total count: $symbolsCount")
        val translatedLines = translator.translateLines(originalLines, "en", "uk")
        writeToConsole("Translation finished successfully. ")
        val outputText = joinLines(translatedLines, originalLines)

        if (outputPath.exists() && APPEND.flag == outputTreatment) {
            appendFile(outputPath, outputText)
            writeToConsole("Translation was successfully appended in the output file.")
            cleanFile(inputPath)
            writeToConsole("Input file: $inputPath was cleaned.")
            return
        }

        writeToFile(outputPath, outputText)
        writeToConsole("Translation was successfully written in the output file.")
        cleanFile(inputPath)
        writeToConsole("Input file: $inputPath was cleaned.")
    } catch (e: InvalidPathException) {
        println("Invalid path provided: ${e.message}")
    } catch (e: Throwable) {
        println("An error occurred: ${e.message}")
    }
}


