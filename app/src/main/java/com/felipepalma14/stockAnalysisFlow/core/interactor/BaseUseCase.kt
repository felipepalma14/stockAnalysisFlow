package com.felipepalma14.stockAnalysisFlow.core.interactor

import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

abstract class BaseUseCase<in Params, out Type> where Type : Any {

    abstract suspend fun run(params: Params): Flow<Either<Failure, Type>>

    open operator fun invoke(
        job: Job,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = CoroutineScope(job + Dispatchers.IO).async { run(params) }
        CoroutineScope(job + Dispatchers.Main).launch {
            val await = backgroundJob.await()
            await.catch {
                onResult(Either.Left(Failure.ServerError))
            }.collect { d -> onResult(d) }
        }
    }
}