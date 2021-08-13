package com.felipepalma14.stockAnalysisFlow.features.stock.ui

import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.features.customview.StocksSearchView
import com.felipepalma14.stockAnalysisFlow.features.domain.model.Status
import com.felipepalma14.stockAnalysisFlow.features.domain.model.Stock
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.viewmodel.StocksInfoViewModel
import com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter.StocksInfoAdapter
import com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter.StocksTypeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockInfoActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var rvStocksType: RecyclerView
    private lateinit var rvStocksInfo: RecyclerView
    private lateinit var svSearchStocks: StocksSearchView

    private val listStocksTypes = listOf("Todas", "Financeiro", "Petróleo & Gás", "Energia")
    private val stocksInfoViewModel: StocksInfoViewModel by viewModels()
    private val TAG = "StockInfoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_analisys)
        setupView()
        setupViewModel()

    }

    private fun setupView(){
        tvTitle = findViewById(R.id.tvTitle)
        rvStocksType = findViewById(R.id.rvStocksType)
        rvStocksInfo = findViewById(R.id.rvStocksInfo)
        svSearchStocks = findViewById(R.id.svSearchStocks)

        val spannable = SpannableString(getString(R.string.stocks_analisys_title))
        spannable.setSpan(
            StyleSpan(BOLD),
            11, spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvTitle.text = spannable

        setupAdapter()

    }

    private fun setupViewModel(){
        stocksInfoViewModel.getStocks()
        stocksInfoViewModel.stockState.observe(this, {
            when(it.status){
                Status.LOADING -> {
                    Log.d(TAG, "setupViewModel: loading")
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "setupViewModel: SUCCESS")
                    it.data?.let { stocks -> setupStocksInfoAdapter(stocks) }
                }
                Status.EMPTY_LIST -> {
                    Log.d(TAG, "setupViewModel: empty")
                }
                Status.ERROR -> {
                    Log.d(TAG, "setupViewModel: ${it.message}")
                }
            }
        })
    }



    private fun setupAdapter(){
        val adapter = StocksTypeAdapter(context = this, listStocksTypes, rvStocksType) { selectedStocksType ->
            Toast.makeText(this, "Item: $selectedStocksType", Toast.LENGTH_LONG).show()
        }
        rvStocksType.adapter = adapter
    }

    private fun setupStocksInfoAdapter(stocks: List<Stock>){
        val adapter = StocksInfoAdapter(context = this, stocks) { selectedStocksType ->
            Toast.makeText(this, "Item: $selectedStocksType", Toast.LENGTH_LONG).show()
        }
        rvStocksInfo.adapter = adapter
    }
}