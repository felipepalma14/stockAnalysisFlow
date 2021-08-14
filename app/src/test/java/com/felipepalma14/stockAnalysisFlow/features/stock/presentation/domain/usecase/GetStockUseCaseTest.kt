package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.usecase

import com.felipepalma14.stockAnalysisFlow.UnitTest
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class GetStockUseCaseTest : UnitTest() {
    private lateinit var useCase: GetStockUseCase

    @MockK
    private lateinit var repository: IStockRepository

    @Before
    fun setUp() {
        useCase = GetStockUseCase(repository)
    }

    @Test
    fun `should call getStocks from repository`() = runBlockingTest {
        val all = ""
        coEvery { repository.getStocks(all) } returns flow {
            emit(
                Either.Right(emptyList<Stock>())
            )
        }
        useCase.run(all)
        coVerify(exactly = 1) { repository.getStocks(all) }
    }
}