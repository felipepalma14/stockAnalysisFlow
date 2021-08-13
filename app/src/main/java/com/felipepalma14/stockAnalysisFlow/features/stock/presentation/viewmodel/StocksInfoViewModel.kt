package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felipepalma14.stockAnalysisFlow.core.exception.Failure
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Resource
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.usecase.GetStockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class StocksInfoViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase,
) : ViewModel() {
    private val job = Job()
    private val _stockState by lazy { MutableLiveData<Resource<List<Stock>>>() }
    val stockState: LiveData<Resource<List<Stock>>>
        get() = _stockState


    fun getStocks(params:String = "") {
        _stockState.postValue(Resource.loading())
        getStockUseCase(job, params) {
            it.fold(
                ::handleSpecieFailure,
                ::handleSuccess
            )
        }
    }
    private fun handleSuccess(data: List<Stock>) {
        if (data.isEmpty()) {
            _stockState.postValue(Resource.emptyList())
        } else {
            _stockState.postValue(Resource.success(data = data))
        }
    }

    private fun handleSpecieFailure(failure: Failure) {
        _stockState.postValue(Resource.error(failure = failure, null))
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

}