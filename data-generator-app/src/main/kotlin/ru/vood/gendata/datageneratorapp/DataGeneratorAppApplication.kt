package ru.vood.gendata.datageneratorapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataGeneratorAppApplication

fun main(args: Array<String>) {
    runApplication<DataGeneratorAppApplication>(*args)
}
