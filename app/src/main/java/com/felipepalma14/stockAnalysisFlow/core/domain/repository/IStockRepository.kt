package com.felipepalma14.stockAnalysisFlow.core.domain.repository

interface IStockRepository {
    suspend fun getStocks()
}