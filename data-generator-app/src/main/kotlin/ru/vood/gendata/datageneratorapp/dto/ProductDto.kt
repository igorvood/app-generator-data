package ru.vood.gendata.datageneratorapp.dto

import java.time.Instant


enum class ProductType{
    T1,
    T2,
}

data class ProductDto(
    val type: ProductType,
    val beginDate: Instant,
    val endDate: Instant? = null,
)
