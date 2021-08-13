package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.usecase

import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.core.interactor.BaseUseCase
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStockUseCase @Inject constructor(
    private val repository: IStockRepository
) : BaseUseCase<String, List<Stock>>() {

    override suspend fun run(params: String): Flow<Either<Failure, List<Stock>>> {
        return repository.getStocks(params)
    }

}