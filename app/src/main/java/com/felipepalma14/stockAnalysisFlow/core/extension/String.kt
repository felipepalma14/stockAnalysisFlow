package com.felipepalma14.stockAnalysisFlow.core.extension

import java.text.DecimalFormat
import java.text.NumberFormat

fun String?.toBRCurrency(): String {
    val formatter: NumberFormat = DecimalFormat("###,###,##0.00")
    return try {
        formatter.format(orDefault())
    }catch (e: Exception){
        formatter.format(0.0)
    }
}

fun String?.orDefault(defaultValue: Double = 0.0): Double {
    var value = defaultValue
    if(!this.isNullOrEmpty()) {
        this.toDoubleOrNull()?.let {
            value = it
        }
    }
    return value
}