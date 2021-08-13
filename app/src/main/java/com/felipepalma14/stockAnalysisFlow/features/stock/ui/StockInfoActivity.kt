package com.felipepalma14.stockAnalysisFlow.features.stock.ui

import android.content.Intent
import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.core.Constants.BUY
import com.felipepalma14.stockAnalysisFlow.core.Constants.COMPRA
import com.felipepalma14.stockAnalysisFlow.core.Constants.INTENT_STOCK_URL
import com.felipepalma14.stockAnalysisFlow.core.Constants.NEUTRAL
import com.felipepalma14.stockAnalysisFlow.core.Constants.NEUTRO
import com.felipepalma14.stockAnalysisFlow.core.Constants.RESTRICTED
import com.felipepalma14.stockAnalysisFlow.core.Constants.TODAS
import com.felipepalma14.stockAnalysisFlow.core.Constants.VENDA
import com.felipepalma14.stockAnalysisFlow.core.extension.hideKeyboard
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.customview.StocksSearchView
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.customview.StocksSearchView.ActionFilter
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Status
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock
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
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    private lateinit var stocksInfoAdapter: StocksInfoAdapter

    private val listStocksTypes = listOf(TODAS, COMPRA, VENDA, NEUTRO)
    private val stocksInfoViewModel: StocksInfoViewModel by viewModels()
    private val TAG = "StockInfoActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_analisys)
        setupView()
        setupViewModel()

    }

    private fun setupView() {
        tvTitle = findViewById(R.id.tvTitle)
        rvStocksType = findViewById(R.id.rvStocksType)
        rvStocksInfo = findViewById(R.id.rvStocksInfo)
        svSearchStocks = findViewById(R.id.svSearchStocks)
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container)

        val spannable = SpannableString(getString(R.string.stocks_analisys_title))
        spannable.setSpan(
            StyleSpan(BOLD),
            11, spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvTitle.text = spannable
        setupAdapter()

    }

    private fun setupViewModel() {
        stocksInfoViewModel.getStocks()
        stocksInfoViewModel.stockState.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    rvStocksInfo.visibility = View.GONE
                    shimmerFrameLayout.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    Log.d(TAG, "setupViewModel: SUCCESS")
                    it.data?.let { stocks -> setupStocksInfoAdapter(stocks) }
                    rvStocksInfo.visibility = View.VISIBLE
                    shimmerFrameLayout.visibility = View.GONE

                }
                Status.EMPTY_LIST -> {
                    Log.d(TAG, "setupViewModel: empty")
                    shimmerFrameLayout.visibility = View.GONE
                }
                Status.ERROR -> {
                    Log.d(TAG, "setupViewModel: ${it.message}")
                    shimmerFrameLayout.visibility = View.GONE
                }
            }
        })
    }


    private fun setupAdapter() {
        val adapter =
            StocksTypeAdapter(context = this, listStocksTypes, rvStocksType) { selectedStocksType ->
                when (selectedStocksType) {
                    TODAS -> {
                        stocksInfoViewModel.getStocks()
                    }
                    VENDA -> {
                        stocksInfoViewModel.getStocks(RESTRICTED)
                    }
                    COMPRA -> {
                        stocksInfoViewModel.getStocks(BUY)
                    }
                    NEUTRO -> {
                        stocksInfoViewModel.getStocks(NEUTRAL)
                    }
                }
            }
        rvStocksType.adapter = adapter
    }

    private fun setupStocksInfoAdapter(stocks: List<Stock>) {
        stocksInfoAdapter = StocksInfoAdapter(context = this, stocks) { stock ->
            val intent = Intent(this, StockDetailsActivity::class.java)
            intent.putExtra(INTENT_STOCK_URL, stock.link)
            startActivity(intent)
        }
        rvStocksInfo.setHasFixedSize(true)
        rvStocksInfo.adapter = stocksInfoAdapter
        svSearchStocks.setupActionFilterListener(object: ActionFilter{
            override fun onTextChanged(query: String) {
                stocksInfoAdapter.filter.filter(query)
            }
            override fun onClickCancel() {
                hideKeyboard()
            }

        })
    }
}