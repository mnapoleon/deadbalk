package org.napoleon.deadbalk.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader

@Component
class NameGenerator(
    @Value("classpath:first_names.txt")
    private val firstNamesFile: Resource,
    @Value("classpath:last_names.txt")
private val lastNamesFile: Resource
) {
    private val firstNames: List<String> by lazy {
        val inputStream = firstNamesFile.inputStream
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readLines()
        }
    }

    private val lastNames: List<String> by lazy {
        val inputStream = lastNamesFile.inputStream
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.readLines()
        }
    }

    fun getRandomFirstName(): String =
        firstNames.shuffled().first()

    fun getRandomLastName(): String =
        lastNames.shuffled().first()

    fun getRandomName(): String =
        "${firstNames.shuffled().first()} ${lastNames.shuffled().first()}"
}