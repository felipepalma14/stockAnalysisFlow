package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.repository

import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import kotlinx.coroutines.flow.Flow

interface IStockRepository {
    suspend fun getStocks(query: String = "") : Flow<Either<Failure, List<Stock>>>
}