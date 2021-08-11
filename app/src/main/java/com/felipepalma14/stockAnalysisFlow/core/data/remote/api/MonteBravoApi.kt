package com.felipepalma14.stockAnalysisFlow.core.data.remote.api

import com.felipepalma14.stockAnalysisFlow.core.data.remote.model.StockResponse
import retrofit2.Response
import retrofit2.http.GET

interface MonteBravoApi {
    @GET("estimates?origin=XP")
    fun fetchStocks(): Response<StockResponse>
}