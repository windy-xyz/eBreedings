package com.wcs.ebreedings.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wcs.ebreedings.databinding.FragmentFfbHistoryBinding
import kotlinx.coroutines.runBlocking

class FfbHistoryFragment: Fragment() {

    private var _binding: FragmentFfbHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFfbHistoryBinding.inflate(inflater, container, false)

        val historyView = _binding?.ffbHistoryView
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        historyView!!.layoutManager = layoutManager

        runBlocking {
            val ffbYieldList = HistoryDataSource().getAllRecordings(container!!.context)

            val adapter = FfbHistoryAdapter(ffbYieldList)
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
