package com.wcs.ebreedings.ui.palmInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.wcs.ebreedings.R
import com.wcs.ebreedings.data.entity.PalmEntity
import com.wcs.ebreedings.databinding.FragmentPalmInformationBinding
import kotlinx.coroutines.runBlocking

class PalmInformationFragment : Fragment() {

    private var _binding: FragmentPalmInformationBinding? = null
    private val binding get() = _binding!!
    private var menu: String = ""
    private var palms: List<PalmEntity>? = null
    private var qrCodeNo = ""
    private var shortPalmCode = ""
    private var palmCode = ""
    private var rowNumber = ""
    private var blockCode = ""
    private var maxBunches = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPalmInformationBinding.inflate(inflater, container, false)

        val bundle = Bundle()

        arguments?.let {
            menu = it.getString("FfbOrVeg", "")
        }

        val barcodeLauncher = registerForActivityResult(ScanContract()) {
            if (it.contents == null) {
                Toast.makeText(this.context, "QR Code Scanner Cancelled", Toast.LENGTH_LONG).show()
            } else {
                qrCodeNo = it.contents
                for (palm in palms!!) {
                    if (palm.qr_code_no == qrCodeNo) {
                        _binding?.palmCode?.setText(palm.palm_code)
                        _binding?.palmNumber?.text = palm.palm_number
                        _binding?.rowNumber?.text = palm.palm_row_number
                        _binding?.progenyId?.text = palm.progeny_id
                        _binding?.parentalMale?.text = palm.parental_male
                        _binding?.parentalFemale?.text = palm.parental_female
                        _binding?.fruitType?.text = palm.fruit_type
                        _binding?.estate?.text = palm.estate_name
                        _binding?.division?.text = palm.division_name
                        _binding?.blockNumber?.text = palm.block_no
                        _binding?.trialArea?.text = palm.trial_area
                        _binding?.yop?.text = palm.year_of_planting
                        _binding?.matureStage?.text = palm.mature_stage
                        _binding?.palmStatus?.text = palm.palm_status2
                        _binding?.maxBunches?.text = palm.max_bunches

                        palmCode = palm.palm_code
                        shortPalmCode = palm.progeny_id + "." + palm.palm_number
                        rowNumber = palm.palm_row_number
                        blockCode = palm.division_code + "/" + palm.block_no + "/" + palm.trial_area
                        maxBunches = palm.max_bunches
                    }
                }
            }
        }

        val scanOptions = ScanOptions()
        scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        scanOptions.setPrompt("Scan a barcode")
        scanOptions.setCameraId(0)
        scanOptions.setOrientationLocked(false)
        scanOptions.setBeepEnabled(false)
        scanOptions.setBarcodeImageEnabled(true)

        runBlocking {
            val _palms = PalmInformationDataSource().getPalms(container!!.context)
            palms = _palms
        }

        _binding?.qrCode?.setOnClickListener {
            barcodeLauncher.launch(scanOptions)
        }

        if (menu == "Ffb") {
            _binding?.nextButton?.setOnClickListener {
                bundle.putString("insert_or_edit", "insert")
                bundle.putString("palm_code", palmCode)
                bundle.putString("short_palm_code", shortPalmCode)
                bundle.putString("row_number", rowNumber)
                bundle.putString("block_code", blockCode)
                bundle.putString("max_bunches", maxBunches)
                findNavController().navigate(R.id.FfbYieldRecordingFragment, bundle)
            }
        } else if (menu == "Veg") {
            _binding?.nextButton?.setOnClickListener {
                bundle.putString("insert_or_edit", "insert")
                bundle.putString("palm_code", palmCode)
                bundle.putString("short_palm_code", shortPalmCode)
                bundle.putString("row_number", rowNumber)
                bundle.putString("block_code", blockCode)
                findNavController().navigate(R.id.VegMeasurementFragment, bundle)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
