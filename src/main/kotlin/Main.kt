package org.demotdd

import config.ConfigReader
import org.demotdd.io.FileProcessor
import org.demotdd.mapping.joinLines
import org.demotdd.parsing.getTranslationList
import org.demotdd.translation.Translator
import org.demotdd.util.defineOs
import org.demotdd.validations.validateInputPath
import org.demotdd.validations.validateOutputPath
import java.nio.file.InvalidPathException
import java.nio.file.Paths

fun main(args: Array<String>) {
    require(args.size >= 2) { "Please provide input and output file paths as arguments." }

    try {
        val inputPath = Paths.get(args[0])
        val outputPath = Paths.get(args[1])

        validateInputPath(inputPath)
        validateOutputPath(outputPath, defineOs(System.getProperty("os.name")))

        val fileProcessor = FileProcessor()

        val inputText = fileProcessor.readFromFile(inputPath)

        val originalLines = getTranslationList(inputText)

        val configReader = ConfigReader()

        val apiKey = configReader.getApiKey() ?: throw Exception("Translation is not possible without API key")
        val translatedLines = Translator(apiKey).translateLines(originalLines, "en", "uk")

        val outputText = joinLines(translatedLines, originalLines)

        fileProcessor.writeToFile(outputPath, outputText)
    } catch (e: InvalidPathException) {
        println("Invalid path provided.")
    } catch (e: Throwable) {
        println("An error occurred: ${e.message}")
    }
}

