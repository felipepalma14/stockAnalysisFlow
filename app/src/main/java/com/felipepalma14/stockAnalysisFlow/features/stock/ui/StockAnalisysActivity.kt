package com.felipepalma14.stockAnalysisFlow.features.stock.ui

import android.graphics.Typeface.BOLD
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.features.customview.StocksSearchView
import com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter.StocksTypeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockAnalisysActivity : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var rvStocksType: RecyclerView
    private lateinit var rvStocksInfo: RecyclerView
    private lateinit var svSearchStocks: StocksSearchView

    private val listStocksTypes = listOf("Todas", "Financeiro", "Petróleo & Gás", "Energia")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_analisys)
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

    private fun setupAdapter(){
        val adapter = StocksTypeAdapter(context = this, listStocksTypes, rvStocksType) { selectedStocksType ->
            Toast.makeText(this, "Item: $selectedStocksType", Toast.LENGTH_LONG).show()
        }
        rvStocksType.adapter = adapter
    }
}