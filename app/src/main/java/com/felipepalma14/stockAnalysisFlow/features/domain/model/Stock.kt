package com.felipepalma14.stockAnalysisFlow.features.domain.model

data class Stock(
    val id: Int,
    val analysts: String?,
    val companyName: String?,
    val currentPrice: String?,
    val date: String?,
    val isSourceFavoriteRecomendation: Boolean = false,
    val link: String?,
    val origin: String?,
    val potentialPrice: String?,
    val recomendation: String?,
    val ref: String?,
    val symbol: String?,
    val symbolImageUrl: String,
    val targetPrice: String?
)