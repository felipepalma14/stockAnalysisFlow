package com.felipepalma14.stockAnalysisFlow.features.stock.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.felipepalma14.stockAnalysisFlow.R

class StocksInfoAdapter(
    private val context:Context,
    private val list: List<String>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<StocksInfoAdapter.ViewHolder>() {
    private var selectedItem: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stocks_info_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[holder.adapterPosition]
        holder.tvLabel.text = item
        holder.container.setOnClickListener {
            listener.invoke(item)
        }
    }

    override fun getItemCount() = list.size

    private fun setItemActivate(holder: ViewHolder){
        holder.tvLabel.setTextColor(Color.WHITE)
        holder.container.setBackgroundResource(R.drawable.background_item_stocks_types_card_active)
    }

    private fun setItemInactivate(holder: ViewHolder){
        holder.tvLabel.setTextColor(ContextCompat.getColor(context,R.color.on_stock_type_inactive))
        holder.container.setBackgroundResource(R.drawable.background_item_stocks_types_card_inactive)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLabel: TextView = this.itemView.findViewById(R.id.tvLabel)
        val container: ConstraintLayout = this.itemView.findViewById(R.id.container)
    }
}