package com.wcs.ebreedings.ui.vegMeasurment

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.ULocale
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.wcs.ebreedings.R
import com.wcs.ebreedings.data.entity.VegMeasEntity
import com.wcs.ebreedings.data.storage.Pref
import com.wcs.ebreedings.databinding.FragmentVegMeasurementBinding
import com.wcs.ebreedings.others.DateConverter
import com.wcs.ebreedings.ui.login.afterTextChanged
import kotlinx.coroutines.runBlocking
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class VegMeasurementFragment: Fragment() {
    private var _binding: FragmentVegMeasurementBinding? = null
    private val binding get() = _binding!!
    private var doAction: String = ""
    private var palmCode: String = ""
    private var shortPalmCode: String = ""
    private var rowNumber: String = ""
    private var blockCode: String = ""
    private var rowId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVegMeasurementBinding.inflate(inflater, container, false)

        val _vegPalmCode = _binding?.vegPalmCode
        val _vegDate = _binding?.vegDate
        val _vegLeafSamNo = _binding?.vegLeafSamNo
        val _vegPalmHeight = _binding?.vegPalmHeight
        val _vegPetioleWidth = _binding?.vegPetioleWidth
        val _vegPetioleThickness = _binding?.vegPetioleThickness
        val _vegPetioleCrossSection = _binding?.vegPetioleCrossSection
        val _vegSample1Length = _binding?.vegSample1Length
        val _vegSample1Width = _binding?.vegSample1Width
        val _vegSample2Length = _binding?.vegSample2Length
        val _vegSample2Width = _binding?.vegSample2Width
        val _vegSample3Length = _binding?.vegSample3Length
        val _vegSample3Width = _binding?.vegSample3Width
        val _vegSample4Length = _binding?.vegSample4Length
        val _vegSample4Width = _binding?.vegSample4Width
        val _vegSample5Length = _binding?.vegSample5Length
        val _vegSample5Width = _binding?.vegSample5Width
        val _vegSample6Length = _binding?.vegSample6Length
        val _vegSample6Width= _binding?.vegSample6Width
        val _vegNumOfLeaflets = _binding?.vegNumOfLeaflets
        val _vegFrondLength = _binding?.vegFrondLength
        val _vegFrondProduction = _binding?.vegFrondProduction
        val _vegLeafArea = _binding?.vegLeafArea
        val _vegPalmTrunkCircum = _binding?.vegPalmTrunkCircum
        val _vegSupervisor = _binding?.vegSupervisor
        val _vegSurveyor = _binding?.vegSurveyor
        val _vegSaveButton = _binding?.vegSaveButton

        arguments?.let {
            doAction = it.getString("insert_or_edit", "")
            palmCode = it.getString("palm_code", "")
            shortPalmCode = it.getString("short_palm_code", "")
            rowNumber = it.getString("row_number", "")
            blockCode = it.getString("block_code", "")
            rowId = it.getInt("row_id")
        }

        if (doAction == "insert") {
            // Action edit existing data
            _vegPalmCode?.setText(palmCode)

            val calendar = Calendar.getInstance(ULocale("ID"))
            calendar.time = Date()
            _vegDate?.setText(DateConverter.convertDate(calendar))

            _vegPetioleWidth?.afterTextChanged {
                val width: Double = if (_vegPetioleWidth.text?.isBlank()!!) 0.0  else _vegPetioleWidth.text.toString().toDouble()
                val thickness: Double = if (_vegPetioleThickness?.text?.isBlank()!!) 0.0 else _vegPetioleThickness.text.toString().toDouble()

                val cross: Double = width*thickness/100
                _vegPetioleCrossSection?.setText(roundOffDecimal(cross).toBigDecimal().toPlainString())
            }

            _vegPetioleThickness?.afterTextChanged {
                val width: Double = if (_vegPetioleWidth?.text?.isBlank()!!) 0.0  else _vegPetioleWidth.text.toString().toDouble()
                val thickness: Double = if (_vegPetioleThickness.text?.isBlank()!!) 0.0 else _vegPetioleThickness.text.toString().toDouble()

                val cross: Double = width*thickness/100
                _vegPetioleCrossSection?.setText(roundOffDecimal(cross).toBigDecimal().toPlainString())
            }

            _vegSample1Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample1Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample2Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample2Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample3Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample3Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample4Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample4Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample5Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample5Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample6Length?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSample6Width?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegNumOfLeaflets?.afterTextChanged {
                val result = getLeafArea(
                    _vegSample1Length, _vegSample1Width, _vegSample2Length, _vegSample2Width, _vegSample3Length, _vegSample3Width,
                    _vegSample4Length, _vegSample4Width, _vegSample5Length, _vegSample5Width, _vegSample6Length, _vegSample6Width, _vegNumOfLeaflets)

                _vegLeafArea?.setText(result)
            }

            _vegSupervisor?.afterTextChanged {
                _vegSaveButton?.isEnabled = _vegSupervisor.text.toString() != "" && _vegSurveyor?.text.toString() != ""
            }

            _vegSurveyor?.afterTextChanged {
                _vegSaveButton?.isEnabled = _vegSupervisor?.text.toString() != "" && _vegSurveyor.text.toString() != ""
            }

            _vegSupervisor?.setText(VegMeasurementDataSource().getSupervisor(container!!.context))
            _vegSurveyor?.setText(VegMeasurementDataSource().getSurveyor(container!!.context))
        } else {
            // Action edit existing data
            val measurement = setData(container!!.context, rowId)

            _vegPalmCode?.setText(measurement.palm_code)
            _vegDate?.setText(measurement.date_veg_meas)
            _vegLeafSamNo?.setText(measurement.leaf_sam_no.toString())
            _vegPalmHeight?.setText(measurement.palm_height.toString())
            _vegPetioleWidth?.setText(measurement.petiole_width.toString())
            _vegPetioleThickness?.setText(measurement.petiole_thickness.toString())
            _vegPetioleCrossSection?.setText(measurement.pet_cross_sec.toString())
            _vegSample1Length?.setText(measurement.leaflets_sam1_length.toString())
            _vegSample1Width?.setText(measurement.leaflets_sam1_width.toString())
            _vegSample2Length?.setText(measurement.leaflets_sam2_length.toString())
            _vegSample2Width?.setText(measurement.leaflets_sam2_width.toString())
            _vegSample3Length?.setText(measurement.leaflets_sam3_length.toString())
            _vegSample3Width?.setText(measurement.leaflets_sam3_width.toString())
            _vegSample4Length?.setText(measurement.leaflets_sam4_length.toString())
            _vegSample4Width?.setText(measurement.leaflets_sam4_width.toString())
            _vegSample5Length?.setText(measurement.leaflets_sam5_length.toString())
            _vegSample5Width?.setText(measurement.leaflets_sam5_width.toString())
            _vegSample6Length?.setText(measurement.leaflets_sam6_length.toString())
            _vegSample6Width?.setText(measurement.leaflets_sam6_width.toString())
            _vegNumOfLeaflets?.setText(measurement.num_of_leaflets.toString())
            _vegFrondLength?.setText(measurement.palms_frond_length.toString())
            _vegFrondProduction?.setText(measurement.palms_frond_prod.toString())
            _vegLeafArea?.setText(measurement.leaf_area.toString())
            _vegPalmTrunkCircum?.setText(measurement.palm_trunk_circum.toString())
            _vegSupervisor?.setText(measurement.supervision)
            _vegSurveyor?.setText(measurement.record_officer)
        }

        _vegSaveButton?.setOnClickListener {
            if (doAction == "insert") {
                VegMeasurementDataSource().saveVegMeasurement(
                    container!!.context,
                    _vegDate?.text.toString(),
                    palmCode,
                    shortPalmCode,
                    rowNumber,
                    blockCode,
                    if (_vegLeafSamNo?.text.toString() == "") 0 else _vegLeafSamNo?.text.toString().toInt(),
                    if (_vegPalmHeight?.text.toString() == "") 0.0 else _vegPalmHeight?.text.toString().toDouble(),
                    if (_vegPetioleWidth?.text.toString() == "") 0.0 else _vegPetioleWidth?.text.toString().toDouble(),
                    if (_vegPetioleThickness?.text.toString() == "") 0.0 else _vegPetioleThickness?.text.toString().toDouble(),
                    if (_vegPetioleCrossSection?.text.toString() == "") 0.0 else _vegPetioleCrossSection?.text.toString().toDouble(),
                    if (_vegSample1Width?.text.toString() == "") 0.0 else _vegSample1Width?.text.toString().toDouble(),
                    if (_vegSample1Length?.text.toString() == "") 0.0 else _vegSample1Length?.text.toString().toDouble(),
                    if (_vegSample2Width?.text.toString() == "") 0.0 else _vegSample2Width?.text.toString().toDouble(),
                    if (_vegSample2Length?.text.toString() == "") 0.0 else _vegSample2Length?.text.toString().toDouble(),
                    if (_vegSample3Width?.text.toString() == "") 0.0 else _vegSample3Width?.text.toString().toDouble(),
                    if (_vegSample3Length?.text.toString() == "") 0.0 else _vegSample3Length?.text.toString().toDouble(),
                    if (_vegSample4Width?.text.toString() == "") 0.0 else _vegSample4Width?.text.toString().toDouble(),
                    if (_vegSample4Length?.text.toString() == "") 0.0 else _vegSample4Length?.text.toString().toDouble(),
                    if (_vegSample5Width?.text.toString() == "") 0.0 else _vegSample5Width?.text.toString().toDouble(),
                    if (_vegSample5Length?.text.toString() == "") 0.0 else _vegSample5Length?.text.toString().toDouble(),
                    if (_vegSample6Width?.text.toString() == "") 0.0 else _vegSample6Width?.text.toString().toDouble(),
                    if (_vegSample6Length?.text.toString() == "") 0.0 else _vegSample6Length?.text.toString().toDouble(),
                    if (_vegNumOfLeaflets?.text.toString() == "") 0.0 else _vegNumOfLeaflets?.text.toString().toDouble(),
                    if (_vegFrondLength?.text.toString() == "") 0.0 else _vegFrondLength?.text.toString().toDouble(),
                    if (_vegFrondProduction?.text.toString() == "") 0.0 else _vegFrondProduction?.text.toString().toDouble(),
                    if (_vegLeafArea?.text.toString() == "") 0.0 else _vegLeafArea?.text.toString().toDouble(),
                    if (_vegPalmTrunkCircum?.text.toString() == "") 0.0 else _vegPalmTrunkCircum?.text.toString().toDouble(),
                    _vegSupervisor?.text.toString(),
                    _vegSurveyor?.text.toString(),
                    getUsername(container.context)
                )
                Toast.makeText(this.context, "Insert new data successfully", Toast.LENGTH_SHORT).show()
            } else {
                VegMeasurementDataSource().updateVegMeasurement(
                    container!!.context,
                    rowId,
                    if (_vegLeafSamNo?.text.toString() == "") 0 else _vegLeafSamNo?.text.toString().toInt(),
                    if (_vegPalmHeight?.text.toString() == "") 0.0 else _vegPalmHeight?.text.toString().toDouble(),
                    if (_vegPetioleWidth?.text.toString() == "") 0.0 else _vegPetioleWidth?.text.toString().toDouble(),
                    if (_vegPetioleThickness?.text.toString() == "") 0.0 else _vegPetioleThickness?.text.toString().toDouble(),
                    if (_vegPetioleCrossSection?.text.toString() == "") 0.0 else _vegPetioleCrossSection?.text.toString().toDouble(),
                    if (_vegSample1Width?.text.toString() == "") 0.0 else _vegSample1Width?.text.toString().toDouble(),
                    if (_vegSample1Length?.text.toString() == "") 0.0 else _vegSample1Length?.text.toString().toDouble(),
                    if (_vegSample2Width?.text.toString() == "") 0.0 else _vegSample2Width?.text.toString().toDouble(),
                    if (_vegSample2Length?.text.toString() == "") 0.0 else _vegSample2Length?.text.toString().toDouble(),
                    if (_vegSample3Width?.text.toString() == "") 0.0 else _vegSample3Width?.text.toString().toDouble(),
                    if (_vegSample3Length?.text.toString() == "") 0.0 else _vegSample3Length?.text.toString().toDouble(),
                    if (_vegSample4Width?.text.toString() == "") 0.0 else _vegSample4Width?.text.toString().toDouble(),
                    if (_vegSample4Length?.text.toString() == "") 0.0 else _vegSample4Length?.text.toString().toDouble(),
                    if (_vegSample5Width?.text.toString() == "") 0.0 else _vegSample5Width?.text.toString().toDouble(),
                    if (_vegSample5Length?.text.toString() == "") 0.0 else _vegSample5Length?.text.toString().toDouble(),
                    if (_vegSample6Width?.text.toString() == "") 0.0 else _vegSample6Width?.text.toString().toDouble(),
                    if (_vegSample6Length?.text.toString() == "") 0.0 else _vegSample6Length?.text.toString().toDouble(),
                    if (_vegNumOfLeaflets?.text.toString() == "") 0.0 else _vegNumOfLeaflets?.text.toString().toDouble(),
                    if (_vegFrondLength?.text.toString() == "") 0.0 else _vegFrondLength?.text.toString().toDouble(),
                    if (_vegFrondProduction?.text.toString() == "") 0.0 else _vegFrondProduction?.text.toString().toDouble(),
                    if (_vegLeafArea?.text.toString() == "") 0.0 else _vegLeafArea?.text.toString().toDouble(),
                    if (_vegPalmTrunkCircum?.text.toString() == "") 0.0 else _vegPalmTrunkCircum?.text.toString().toDouble(),
                    _vegSupervisor?.text.toString(),
                    _vegSurveyor?.text.toString()
                )
                Toast.makeText(this.context, "Update data successfully", Toast.LENGTH_SHORT).show()
            }
            VegMeasurementDataSource().setSupervisorSurveyor(container.context, _vegSupervisor?.text.toString(), _vegSurveyor?.text.toString())
            findNavController().navigate(R.id.HomeFragment)
        }

        if (_vegSupervisor?.text.toString() == "" && _vegSurveyor?.text.toString() == "") {
            _vegSaveButton?.isEnabled = false
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateDatePicker(): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        return datePicker.build()
    }

    private fun getUsername(context: Context): String {
        val pref = Pref(context)
        return pref.getUsername()
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

    private fun getLeafArea(
        sample1Length: TextInputEditText?,
        sample1Width: TextInputEditText?,
        sample2Length: TextInputEditText?,
        sample2Width: TextInputEditText?,
        sample3Length: TextInputEditText?,
        sample3Width: TextInputEditText?,
        sample4Length: TextInputEditText?,
        sample4Width: TextInputEditText?,
        sample5Length: TextInputEditText?,
        sample5Width: TextInputEditText?,
        sample6Length: TextInputEditText?,
        sample6Width: TextInputEditText?,
        numOfLeaflets: TextInputEditText?
    ): String {
        val sam1Length: Double = if (sample1Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample1Length.text.toString().toDouble())
        val sam1Width: Double = if (sample1Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample1Width.text.toString().toDouble())
        val sam2Length: Double = if (sample2Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample2Length.text.toString().toDouble())
        val sam2Width: Double = if (sample2Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample2Width.text.toString().toDouble())
        val sam3Length: Double = if (sample3Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample3Length.text.toString().toDouble())
        val sam3Width: Double = if (sample3Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample3Width.text.toString().toDouble())
        val sam4Length: Double = if (sample4Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample4Length.text.toString().toDouble())
        val sam4Width: Double = if (sample4Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample4Width.text.toString().toDouble())
        val sam5Length: Double = if (sample5Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample5Length.text.toString().toDouble())
        val sam5Width: Double = if (sample5Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample5Width.text.toString().toDouble())
        val sam6Length: Double = if (sample6Length!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample6Length.text.toString().toDouble())
        val sam6Width: Double = if (sample6Width!!.text!!.isBlank()) 0.0 else roundOffDecimal(sample6Width.text.toString().toDouble())
        val leaflets: Double = if (numOfLeaflets!!.text!!.isBlank()) 0.0 else roundOffDecimal(numOfLeaflets.text.toString().toDouble())

        val result: Double = ((((sam1Length * sam1Width) + (sam2Length * sam2Width) + (sam3Length * sam3Width) +
                (sam4Length * sam4Width) + (sam5Length * sam5Width) + (sam6Length * sam6Width)) / 6 ) * 2 *
                leaflets * 0.51 / 1000000)

        return (roundOffDecimal(result)).toString()
    }

    private fun setData(context: Context, rowId: Int): VegMeasEntity {
        var measurement: VegMeasEntity
        runBlocking {
            val mes = VegMeasurementDataSource().getMeasurementById(context, rowId)
            measurement = mes
        }
        return measurement
    }

}
