package com.felipepalma14.stockAnalysisFlow.core.data.repository

import com.felipepalma14.stockAnalysisFlow.UnitTest
import com.felipepalma14.stockAnalysisFlow.core.data.remote.api.MonteBravoApi
import com.felipepalma14.stockAnalysisFlow.core.data.remote.model.Message
import com.felipepalma14.stockAnalysisFlow.core.data.remote.model.Result
import com.felipepalma14.stockAnalysisFlow.core.data.remote.model.StockResponse
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class StockRepositoryImpTest : UnitTest() {
    private lateinit var stockRepository: IStockRepository

    @MockK
    private lateinit var apiService: MonteBravoApi

    @MockK
    private lateinit var stockResponse: Response<StockResponse>


    @Before
    fun setUp() {
        stockRepository = StockRepositoryImp(apiService)
    }

    @Test
    fun `getStocks should return empty list if exception occurs in all cases`() = runBlockingTest {
        coEvery { apiService.fetchStocks() } returns stockResponse
        val res = stockRepository.getStocks()
        res.collect { a ->
            assertThat(a).isEqualTo(Either.Right(emptyList<Stock>()))
        }
    }

    @Test
    fun `getStocks should return empty list if response not successful in all cases`() =
        runBlockingTest {
            every { stockResponse.isSuccessful } returns false
            coEvery { apiService.fetchStocks() } returns stockResponse

            val res = stockRepository.getStocks()
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Stock>()))
            }
        }

    @Test
    fun `getStocks should return empty list if response body is null in all cases`() =
        runBlockingTest {
            every { stockResponse.isSuccessful } returns true
            every { stockResponse.body() } returns null
            coEvery { apiService.fetchStocks() } returns stockResponse

            val res = stockRepository.getStocks()
            res.collect { a ->
                assertThat(a).isEqualTo(Either.Right(emptyList<Stock>()))
            }
        }

    @Test
    fun `getStocks responseBody should list of stocks successfully`() = runBlockingTest {
        val messageRes = Message(
            result = listOf(Result(
                id = 19219,
                ref = "xpOMGE3",
                symbol = "OMGE3",
                currentPrice = "39.66",
                targetPrice = "50.00",
                potentialPrice = "26.07",
                recomendation =  "null",
                date = "2021-06-10 12:40:53",
                origin = "XP",
                createdAt = null,
                updatedAt = null,
                link = "https://conteudos.xpi.com.br/acoes/omge3/",
                entryLimitPrice = null,
                analysts =  "Maira Maldonado",
                isSourceFavoriteRecomendation = true,
                symbolImageUrl = "https://conteudos.xpi.com.br/wp-content/uploads/2019/06/Omega.png",
                companyName = "OMEGA GERAÇÃO S.A."
            ),
                Result(
                    id = 79825,
                    ref = "xpASAI3",
                    symbol = "ASAI3",
                    currentPrice = "84.78",
                    targetPrice = "120.00",
                    potentialPrice = "41.54",
                    recomendation =  "null",
                    date = "2021-06-10 12:40:53",
                    origin = "XP",
                    createdAt = null,
                    updatedAt = null,
                    link = "https://conteudos.xpi.com.br/acoes/asai3/",
                    entryLimitPrice = null,
                    analysts =  "Danniela Eiger; Thiago Suedt; Gustavo Senday",
                    isSourceFavoriteRecomendation = true,
                    symbolImageUrl = "https://conteudos.xpi.com.br/wp-content/uploads/2021/03/assai_imprensa.png",
                    companyName = "SENDAS DISTRIBUIDORA S.A."
                ),
            ),
            status = true
        )
        val stockRes = StockResponse(
            message = messageRes,
            status = true
        )
        every { stockResponse.isSuccessful } returns true
        every { stockResponse.body() } returns stockRes
        coEvery { apiService.fetchStocks() } returns stockResponse

        val res = stockRepository.getStocks()
        res.collect { a ->
            val stockMock = messageRes.result.map { it.toDomainObject()}
            assertThat(a).isEqualTo(Either.Right(stockMock))
        }
    }

}

