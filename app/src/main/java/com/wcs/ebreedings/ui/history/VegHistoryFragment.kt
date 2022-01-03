package com.wcs.ebreedings.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wcs.ebreedings.databinding.FragmentVegHistoryBinding
import kotlinx.coroutines.runBlocking

class VegHistoryFragment: Fragment() {

    private var _binding: FragmentVegHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVegHistoryBinding.inflate(inflater, container, false)

        val historyView = _binding?.vegHistoryView
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        historyView!!.layoutManager = layoutManager

        runBlocking {
            val vegMeasurementList = HistoryDataSource().getAllMeasurements(container!!.context)

            val adapter = VegHistoryAdapter(vegMeasurementList)
            historyView.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}