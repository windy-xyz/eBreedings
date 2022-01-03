package com.wcs.ebreedings.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.wcs.ebreedings.R
import com.wcs.ebreedings.data.entity.VegMeasEntity
import com.wcs.ebreedings.ui.history.VegHistoryAdapter.ViewHolder

class VegHistoryAdapter(private val historyList: List<VegMeasEntity>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rowId = historyList[position].measurement_id
        holder.rowDate.text = historyList[position].date_veg_meas
        holder.rowPalmCode.text = historyList[position].short_palm_code
        holder.rowRowNumber.text = historyList[position].row_number
        holder.rowBlockCode.text = historyList[position].block_code

        holder.rowHistoryLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("row_id", holder.rowId)
            bundle.putString("insert_or_edit", "edit")
            val a = it.findFragment<VegHistoryFragment>()
            a.findNavController().navigate(R.id.VegMeasurementFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var rowId: Int = 0
        var rowHistoryLayout: MaterialCardView = itemView.findViewById(R.id.row_history_layout)
        var rowDate: MaterialTextView = itemView.findViewById(R.id.row_date)
        var rowPalmCode: MaterialTextView = itemView.findViewById(R.id.row_palm_code)
        var rowRowNumber: MaterialTextView = itemView.findViewById(R.id.row_row_number)
        var rowBlockCode: MaterialTextView = itemView.findViewById(R.id.row_block_code)
    }

}
