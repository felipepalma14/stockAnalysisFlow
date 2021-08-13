package com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.core.extension.toBRCurrency
import com.felipepalma14.stockAnalysisFlow.features.customview.StockRecommendationView
import com.felipepalma14.stockAnalysisFlow.features.domain.model.Stock

class StocksInfoAdapter(
    private val context:Context,
    private val list: List<Stock>,
    private val listener: (Stock) -> Unit
) : RecyclerView.Adapter<StocksInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stocks_info_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[holder.adapterPosition]
        Glide.with(context)
            .load(item.symbolImageUrl)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.imgStock)
        holder.tvStockCompany.text = item.companyName
        holder.tvStockSymbol.text = item.symbol
        val spannable = SpannableString(context.getString(R.string.adapter_br_currency, item.currentPrice?.toBRCurrency()))
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context,R.color.br_currency_label)),
            0, 2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        holder.tvStockCurrentPrice.text = spannable

        //if(item.recomendation == null) {
        //    holder.srvRecommendation.visibility = View.GONE
        //}else{
        item.recomendation?.let { holder.srvRecommendation.setupStockRecommendation(it) }
        //}

        holder.container.setOnClickListener {
            listener.invoke(item)
        }
    }

    override fun getItemCount() = list.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: CardView = this.itemView.findViewById(R.id.container)
        val imgStock: ImageView = this.itemView.findViewById(R.id.imgStock)
        val tvStockSymbol: TextView = this.itemView.findViewById(R.id.tvStockSymbol)
        val tvStockCompany: TextView = this.itemView.findViewById(R.id.tvStockCompany)
        val tvStockCurrentPrice: TextView = this.itemView.findViewById(R.id.tvStockCurrentPrice)
        val srvRecommendation: StockRecommendationView = this.itemView.findViewById(R.id.srvRecommendation)

    }
}