package ru.vood.gendata.datageneratorapp

import com.ocadotechnology.gembus.test.Arranger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.vood.gendata.datageneratorapp.dto.ProductDto

//@SpringBootApplication
//class DataGeneratorAppApplication

fun main(args: Array<String>) {


    val some = Arranger.some(ProductDto::class.java)

    println(some)

//    runApplication<DataGeneratorAppApplication>(*args)



}
