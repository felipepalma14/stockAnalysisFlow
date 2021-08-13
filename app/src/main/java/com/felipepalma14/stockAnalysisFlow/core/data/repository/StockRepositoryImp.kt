package com.felipepalma14.stockAnalysisFlow.core.data.repository

import android.util.Log
import com.felipepalma14.stockAnalysisFlow.core.data.remote.api.MonteBravoApi
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    private val apiService: MonteBravoApi
) : IStockRepository {
    override suspend fun getStocks(query:String): Flow<Either<Failure, List<Stock>>> = flow {
        val list = mutableListOf<Stock>()
        try {
            var res = apiService.fetchStocks(query)
            if(query.isEmpty()) res = apiService.fetchStocks()

            if (res.isSuccessful && res.body() != null) {
                res.body()?.let {
                    val stocks = it.message.result.map { result ->
                        result.toDomainObject()
                    }
                    list.addAll(stocks)
                }
            }
        } catch (exception: Throwable) {
            Log.d("StockRepositoryImp", "getStocks: ${exception.message}")
        }
        emit(Either.Right(list))
    }

}