package com.felipepalma14.stockAnalysisFlow.core.data.remote.api

import com.felipepalma14.stockAnalysisFlow.core.data.remote.model.StockResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MonteBravoApi {
    @GET("estimates?origin=XP")
    suspend fun fetchStocks(@Query("recommendations[]") query: String): Response<StockResponse>

    @GET("estimates?origin=XP")
    suspend fun fetchStocks(): Response<StockResponse>

}