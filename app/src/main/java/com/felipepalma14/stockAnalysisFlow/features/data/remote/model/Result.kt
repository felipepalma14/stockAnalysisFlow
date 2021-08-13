package com.felipepalma14.stockAnalysisFlow.features.data.remote.model

import com.felipepalma14.stockAnalysisFlow.features.domain.model.Stock
import com.squareup.moshi.Json

data class Result(
    val analysts: String,
    @field:Json(name = "company_name") val companyName: String,
    @field:Json(name = "created_at") val createdAt: Any?,
    @field:Json(name = "curent_price") val currentPrice: String,
    val date: String,
    @field:Json(name = "entry_limit_price") val entryLimitPrice: Any?,
    val id: Int,
    @field:Json(name = "is_source_favorite_recomendation") val isSourceFavoriteRecomendation: Boolean,
    val link: String,
    val origin: String,
    @field:Json(name = "potential_price") val potentialPrice: String,
    val recomendation: String?,
    val ref: String,
    val symbol: String,
    @field:Json(name = "symbol_image_url") val symbolImageUrl: String,
    @field:Json(name = "target_price") val targetPrice: String,
    @field:Json(name = "updated_at") val updatedAt: Any?
) {
    fun toDomainObject() = Stock(
        id,
        analysts,
        companyName,
        currentPrice,
        date,
        isSourceFavoriteRecomendation,
        link,
        origin,
        potentialPrice,
        recomendation,
        ref,
        symbol,
        symbolImageUrl,
        targetPrice
    )
}