package com.wcs.ebreedings.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wcs.ebreedings.R
import com.wcs.ebreedings.api.CallbackInterface
import com.wcs.ebreedings.databinding.FragmentHomeBinding
import com.wcs.ebreedings.ui.sync.SyncDataSource
import com.wcs.ebreedings.ui.sync.SyncResponse

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ffbRecording = _binding?.layoutFfbMeasurement
        val vegMeasurement = _binding?.layoutVegetativeMeasurement
        val ffbHistoryBinding = _binding?.layoutFfbHistory
        val vegHistoryBinding = _binding?.layoutVegHistory
        val dataSync= _binding?.layoutDataSync
        val bundle = Bundle()

        ffbRecording?.setOnClickListener {
            bundle.putString("FfbOrVeg", "Ffb")
            findNavController().navigate(R.id.PalmInformationFragment, bundle)
        }

        vegMeasurement?.setOnClickListener {
            bundle.putString("FfbOrVeg", "Veg")
            findNavController().navigate(R.id.PalmInformationFragment, bundle)
        }

        ffbHistoryBinding?.setOnClickListener {
            findNavController().navigate(R.id.FfbHistoryFragment, bundle)
        }

        vegHistoryBinding?.setOnClickListener {
            findNavController().navigate(R.id.VegHistoryFragment, bundle)
        }

        dataSync?.setOnClickListener {
            SyncDataSource().syncData(view.context, object : CallbackInterface<SyncResponse> {
                override fun onDataResponse(data: SyncResponse?) {
                    Toast.makeText(it.context, "Data has been uploaded successfully", Toast.LENGTH_SHORT).show()
                }

                override fun onDataFailure(data: String?) {
                    Toast.makeText(it.context, data.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
