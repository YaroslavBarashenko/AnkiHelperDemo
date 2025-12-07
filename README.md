# Anki Helper

Anki Helper is a CLI tool written in Kotlin that helps simplify the creation of flashcards for Anki. The program accepts a path to a text file with English sentences and a path to a CSV file to save the results. It uses a third-party translation service to generate a bilingual CSV file.

## Features

- Reads a text file containing English sentences.
- Utilizes a third-party API for translating text into Ukrainian.
- Displays numbered translation pairs in a formatted manner for user review.
- Allows users to modify translations by specifying a number before saving.
- Generates a CSV file in the format:
  ```
  Ukrainian Sentence|English Sentence
  ```
- When the input file length exceed rest of translation service limit, the program selects the longest possible part of the text for translation (only full strings).

## System Requirements

- **JDK**: 8 or above
- Internet connection for using the third-party translation service

## Building the Project

To build the executable JAR file, use the Gradle Shadow plugin:

**On Windows:**

```bash
.\gradlew.bat shadowJar
```

**On Linux/macOS:**

```bash
./gradlew shadowJar
```

The executable JAR file will be created in the `build/libs/` directory with the name `anki-helper-<version>.jar`.

## Usage

### Input File

The input file format is a plain text file where English sentences (may contain symbols not used in translation as *). For example:
```
* Normally the bridge is closed to the public, but there will be a special tour this
afternoon.* Everyone began waving as the boat set sail.
They rented a cabin in the woods where they
could spend all day in nature.

* Save your pay stubs so that at the end of the year, you can see how much
money you’ve contributed to your retirement funds. *
```

### Command-Line Execution

Run the program from the command line by providing arguments:
1. Path to the input file.
2. Path to the output CSV file.
3. (Optional) Output mode: `a` for append or `o` for overwrite.

**Basic usage (overwrites by default):**
```bash
java -jar anki-helper.jar /path/to/input.txt /path/to/output.csv
```

**Append mode (adds translations to existing file):**

```bash
java -jar anki-helper.jar /path/to/input.txt /path/to/output.csv a
```

**Overwrite mode (explicitly replaces file content):**

```bash
java -jar anki-helper.jar /path/to/input.txt /path/to/output.csv o
```

> **Note:** After successful translation, the input file will be automatically cleaned.

### Output File

The program generates a CSV file (using pipe for separation) where each row is in the following format:
```
Зазвичай міст закритий для відвідування, але сьогодні вдень буде спеціальна екскурсія.|Normally the bridge is closed to the public, but there will be a special tour this afternoon.
Усі почали махати руками, коли човен відчалив.|Everyone began waving as the boat set sail.
Вони орендували кабіну в лісі, де вони могли провести весь день на природі.|They rented a cabin in the woods where they could spend all day in nature.
Зберігайте свої виплати, щоб в кінці року ви могли побачити, скільки грошей ви внесли до своїх пенсійних фондів.|Save your pay stubs so that at the end of the year, you can see how much money you’ve contributed to your retirement funds.
```

## Configuration

### Translation API

The program uses a third-party translation service (now Deepl). To configure:
1. Register on the API platform and obtain an access key.
2. Add the key to the `config.properties` file or run application and add enter your key when prompted.
   ```properties
   deepl.api.key=YOUR_API_KEY
   ```

---

> *Note: Additional setup may require initial testing of the translation API.*

## Roadmap

### Translation Review and Editing

After fetching translations, the program displays numbered pairs of sentences for user review in the format:

```
1. Зазвичай міст закритий для відвідування, але сьогодні вдень буде спеціальна екскурсія.
   Normally the bridge is closed to the public, but there will be a special tour this afternoon.
   
2. Усі почали махати, коли човен відпливав.
   Everyone began waving as the boat set sail.
   
3. Вони орендували кабіну в лісі, де вони могли проводити весь день на природі.
   They rented a cabin in the woods where they could spend all day in nature.
   
4. Зберігайте свої виплати, щоб в кінці року ви могли побачити, скільки грошей ви внесли до своїх пенсійних фондів.
   Save your pay stubs so that at the end of the year, you can see how much money you’ve contributed to your retirement funds.
   
```

Users can:

- Enter a number to modify the translation of a specific pair.
- Confirm to save the translations to the output file.

### GUI for files selection and translation editing

### Opportunity to add transcription/audio with pronunciation  for selected words
