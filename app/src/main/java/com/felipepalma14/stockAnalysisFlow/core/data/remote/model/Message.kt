package com.felipepalma14.stockAnalysisFlow.core.data.remote.model

data class Message(
    val result: List<Result>,
    val status: Boolean
)