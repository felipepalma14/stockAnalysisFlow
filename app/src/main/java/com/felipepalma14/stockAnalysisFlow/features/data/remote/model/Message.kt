package com.felipepalma14.stockAnalysisFlow.features.data.remote.model

data class Message(
    val result: List<Result>,
    val status: Boolean
)