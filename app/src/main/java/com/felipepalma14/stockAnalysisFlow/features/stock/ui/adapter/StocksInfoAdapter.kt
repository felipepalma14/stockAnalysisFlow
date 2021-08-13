package com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.felipepalma14.stockAnalysisFlow.R
import com.felipepalma14.stockAnalysisFlow.core.extension.toBRCurrency
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.customview.StockRecommendationView
import com.felipepalma14.stockAnalysisFlow.features.stock.presentation.domain.model.Stock

class StocksInfoAdapter(
    private val context:Context,
    private val list: List<Stock>,
    private val listener: (Stock) -> Unit
) : RecyclerView.Adapter<StocksInfoAdapter.ViewHolder>(), Filterable {

    private var filterList: List<Stock> = listOf()
    init {
        filterList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stocks_info_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return filterList[position].id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return filterList[position].id
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filterList[holder.adapterPosition]
        holder.bind(context, item)
        holder.container.setOnClickListener {
            listener.invoke(item)
        }

    }

    override fun getItemCount() = filterList.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: CardView = this.itemView.findViewById(R.id.container)
        val imgStock: ImageView = this.itemView.findViewById(R.id.imgStock)
        val tvStockSymbol: TextView = this.itemView.findViewById(R.id.tvStockSymbol)
        val tvStockCompany: TextView = this.itemView.findViewById(R.id.tvStockCompany)
        val tvStockCurrentPrice: TextView = this.itemView.findViewById(R.id.tvStockCurrentPrice)
        val srvRecommendation: StockRecommendationView = this.itemView.findViewById(R.id.srvRecommendation)

        fun bind(context: Context, item: Stock){
            Glide.with(context)
                .load(item.symbolImageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imgStock)
            tvStockCompany.text = item.companyName
            tvStockSymbol.text = item.symbol
            val spannable = SpannableString(context.getString(R.string.adapter_br_currency, item.currentPrice?.toBRCurrency()))
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context,R.color.br_currency_label)),
                0, 2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvStockCurrentPrice.text = spannable

            item.recomendation?.let { srvRecommendation.setupStockRecommendation(it) }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterList = if (charSearch.isEmpty()) {
                    list
                } else {
                    val resultList = mutableListOf<Stock>()
                    for (row in list) {
                        row.symbol?.let {
                            if (it.lowercase().contains(constraint.toString().lowercase())) {
                                resultList.add(row)
                            }
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let {
                    val list =  it.values as List<Stock>
                    if(list.isNotEmpty()) {
                        filterList = list
                        notifyDataSetChanged()
                    }
                }

            }
        }
    }
}