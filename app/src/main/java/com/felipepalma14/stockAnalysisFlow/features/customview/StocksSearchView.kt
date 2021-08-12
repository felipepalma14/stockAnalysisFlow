package com.felipepalma14.stockAnalysisFlow.features.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.felipepalma14.stockAnalysisFlow.R

class StocksSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    private var actionFilter: ActionFilter? = null
    private lateinit var edtFilter: EditText
    private lateinit var btnCancel: TextView

    init {
        val view = inflate(context, R.layout.stock_search_view, this)
        setupView(view)
    }

    private fun setupView(view: View){
        edtFilter = view.findViewById(R.id.edtFilter)
        btnCancel = view.findViewById(R.id.btnCancel)

        btnCancel.setOnClickListener {
            edtFilter.setText("")
            btnCancel.visibility = View.GONE
            actionFilter?.onClickCancel()
        }

        edtFilter.apply {
            setOnClickListener {
                edtFilter.requestFocus()
            }
            doAfterTextChanged {
                it?.let {
                    val text = it.toString()
                    actionFilter?.onTextChanged(text)
                    if(text.isNotEmpty()) btnCancel.visibility = View.VISIBLE
                }
            }
        }
    }

    fun setupActionFilterListener(listener: ActionFilter){
        actionFilter = listener
    }
    interface ActionFilter {
        fun onTextChanged(query: String)
        fun onClickCancel()
    }
}