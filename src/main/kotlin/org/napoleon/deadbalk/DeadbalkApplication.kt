package org.napoleon.deadbalk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeadbalkApplication

fun main(args: Array<String>) {
    runApplication<DeadbalkApplication>(*args)
}
