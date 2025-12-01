package org.demotdd

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
        TODO()

    } catch (e: InvalidPathException) {
        println("Invalid path provided.")
    } catch (e: Throwable) {
        println("An error occurred: ${e.message}")
    }
}

