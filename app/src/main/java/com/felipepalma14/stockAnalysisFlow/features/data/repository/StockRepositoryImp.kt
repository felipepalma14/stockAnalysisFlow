package com.felipepalma14.stockAnalysisFlow.features.data.repository

import com.felipepalma14.stockAnalysisFlow.features.data.remote.api.MonteBravoApi
import com.felipepalma14.stockAnalysisFlow.features.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.features.domain.model.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    private val apiService: MonteBravoApi
) : IStockRepository {
    override suspend fun getStocks(): Flow<Either<Failure, List<Stock>>> = flow {
        val list = mutableListOf<Stock>()
        try {
            val res = apiService.fetchStocks()
            if (res.isSuccessful && res.body() != null) {
                res.body()?.let {
                    val stocks = it.message.result.map { result ->
                        result.toDomainObject()
                    }
                    list.addAll(stocks)
                }
            }
        } catch (exception: Throwable) {
        }
        emit(Either.Right(list))
    }

}