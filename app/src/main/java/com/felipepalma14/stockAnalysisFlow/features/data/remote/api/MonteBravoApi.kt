package com.felipepalma14.stockAnalysisFlow.features.data.remote.api

import com.felipepalma14.stockAnalysisFlow.features.data.remote.model.StockResponse
import retrofit2.Response
import retrofit2.http.GET

interface MonteBravoApi {
    @GET("estimates?origin=XP")
    suspend fun fetchStocks(): Response<StockResponse>
}