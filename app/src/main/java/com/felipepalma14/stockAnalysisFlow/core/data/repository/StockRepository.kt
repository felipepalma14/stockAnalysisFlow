package com.felipepalma14.stockAnalysisFlow.core.data.repository

import com.felipepalma14.stockAnalysisFlow.core.data.remote.api.MonteBravoApi
import com.felipepalma14.stockAnalysisFlow.core.domain.repository.IStockRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val apiService: MonteBravoApi
) : IStockRepository{
    override suspend fun getStocks() {
        TODO("Not yet implemented")
    }

}