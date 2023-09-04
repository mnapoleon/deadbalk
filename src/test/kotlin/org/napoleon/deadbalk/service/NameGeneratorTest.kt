package org.napoleon.deadbalk.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeIn
import org.napoleon.deadbalk.services.NameGenerator
import org.springframework.core.io.ClassPathResource

class NameGeneratorTest: ShouldSpec() {
    init {
        val firstNames = ClassPathResource("test_first_names.txt")
        val lastNames = ClassPathResource("test_last_names.txt")
        val nameGenerator = NameGenerator(firstNames, lastNames)

        val expectedFirstNames = listOf("Abe", "James", "Zack")
        val expectedLastNames = listOf("Loki", "Thor", "Frolks")

        context("NameGenerator") {
            should("return a random first name") {
                nameGenerator.getRandomFirstName() shouldBeIn expectedFirstNames
            }

            should("return a random last name") {
                nameGenerator.getRandomLastName() shouldBeIn expectedLastNames
            }
        }
    }
}