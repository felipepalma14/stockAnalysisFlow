package com.felipepalma14.stockAnalysisFlow.features.stock.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.core.Constants.BUY
import com.felipepalma14.stockAnalysisFlow.core.Constants.NEUTRAL
import com.felipepalma14.stockAnalysisFlow.core.Constants.RESTRICTED
import com.felipepalma14.stockAnalysisFlow.core.Constants.SELL

class StockRecommendationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var cvContainer: CardView
    private lateinit var tvRecommendation: TextView

    init {
        val view = inflate(context, R.layout.stock_recommendation_view, this)
        setupView(view)
    }

    private fun setupView(view: View){
        cvContainer = view.findViewById(R.id.cvRecommendation)
        tvRecommendation = view.findViewById(R.id.tvRecommendation)
    }

    fun setupStockRecommendation(type: String){
        when(type){
            BUY -> {
                cvContainer.setCardBackgroundColor(
                    ContextCompat.getColor(context,R.color.buy)
                )
                tvRecommendation.text = context.getString(R.string.recommendation_buy)
                cvContainer.visibility = View.VISIBLE
            }
            SELL, RESTRICTED -> {
                cvContainer.setCardBackgroundColor(
                    ContextCompat.getColor(context,R.color.sell)
                )
                tvRecommendation.text = context.getString(R.string.recommendation_sell)
                cvContainer.visibility = View.VISIBLE

            }
            NEUTRAL -> {
                cvContainer.setCardBackgroundColor(
                    ContextCompat.getColor(context,R.color.neutral)
                )
                tvRecommendation.text = context.getString(R.string.recommendation_neutral)
                cvContainer.visibility = View.VISIBLE

            }
        }
    }
}
