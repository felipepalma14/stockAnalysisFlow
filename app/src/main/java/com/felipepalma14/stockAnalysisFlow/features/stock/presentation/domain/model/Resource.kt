package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model

import com.felipepalma14.stockAnalysisFlow.core.exception.Failure


enum class Status {
    SUCCESS,
    EMPTY_LIST,
    ERROR,
    LOADING
}
class Resource<out T>(val status: Status, val data: T?, val message: Failure?) {
    companion object{
        fun <T> success(data: T?): Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> emptyList(): Resource<T>{
            return Resource(Status.EMPTY_LIST, null, null)
        }
        fun <T> error(failure: Failure, data: T?): Resource<T>{
            return Resource(Status.ERROR, data, failure)
        }
        fun <T> loading(): Resource<T>{
            return Resource(Status.LOADING, null, null)
        }

    }
}