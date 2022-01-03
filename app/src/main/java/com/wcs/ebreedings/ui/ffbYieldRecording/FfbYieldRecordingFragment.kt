package com.wcs.ebreedings.ui.ffbYieldRecording

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
import com.wcs.ebreedings.R
import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.storage.Pref
import com.wcs.ebreedings.databinding.FragmentFfbYieldRecordingBinding
import com.wcs.ebreedings.others.DateConverter
import com.wcs.ebreedings.ui.login.afterTextChanged
import kotlinx.coroutines.runBlocking
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class FfbYieldRecordingFragment: Fragment() {

    private var _binding: FragmentFfbYieldRecordingBinding? = null
    private val binding get() = _binding!!
    private var doAction: String = ""
    private var palmCode: String = ""
    private var shortPalmCode: String = ""
    private var rowNumber: String = ""
    private var blockCode: String = ""
    private var maxBunches: String = ""
    private var rowId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFfbYieldRecordingBinding.inflate(inflater, container, false)

        val _ffbPalmCode = _binding?.ffbPalmCode
        val _ffbDate = _binding?.ffbDate
        val _ffbNumOfFreshBunch = _binding?.ffbNumOfFreshBunch
        val _ffbWeightOfFreshBunch = _binding?.ffbWeightOfFreshBunch
        val _ffbNumOfRottenBunch = _binding?.ffbNumOfRottenBunch
        val _ffbWeightOfRottenBunch = _binding?.ffbWeightOfRottenBunch
        val _ffbRound = _binding?.ffbRound
        val _ffbSupervisor = _binding?.ffbSupervisor
        val _ffbSurveyor = _binding?.ffbSurveyor
        val _ffbSaveButton = _binding?.ffbSaveButton

        arguments?.let {
            doAction = it.getString("insert_or_edit", "")
            palmCode = it.getString("palm_code", "")
            shortPalmCode = it.getString("short_palm_code", "")
            rowNumber = it.getString("row_number", "")
            blockCode = it.getString("block_code", "")
            maxBunches = it.getString("max_bunches", "")
            rowId = it.getInt("row_id")
        }

        if (doAction == "insert") {
            // Action insert new data
            _ffbPalmCode?.setText(palmCode)

            val calendar = Calendar.getInstance(ULocale("ID"))
            calendar.time = Date()
            _ffbDate?.setText(DateConverter.convertDate(calendar))

            _ffbNumOfFreshBunch?.afterTextChanged {
                if (_ffbNumOfFreshBunch.text!!.isNotEmpty() && _ffbNumOfFreshBunch.text.toString().toInt() > maxBunches.toInt()) {
                    _ffbSaveButton?.isEnabled = false
                    Toast.makeText(container!!.context, "The number of fresh bunches can't exceed the maximum number ($maxBunches)", Toast.LENGTH_SHORT).show()
                } else {
                    _ffbSaveButton?.isEnabled = !(_ffbSupervisor?.text.toString() == "" && _ffbSurveyor?.text.toString() == "")
                }
            }

            _ffbSupervisor?.afterTextChanged {
                _ffbSaveButton?.isEnabled = _ffbSupervisor.text.toString() != "" && _ffbSurveyor?.text.toString() != ""
            }

            _ffbSurveyor?.afterTextChanged {
                _ffbSaveButton?.isEnabled = _ffbSupervisor?.text.toString() != "" && _ffbSurveyor.text.toString() != ""
            }

            _ffbSupervisor?.setText(FfbYieldRecordingDataSource().getSupervisor(container!!.context))
            _ffbSurveyor?.setText(FfbYieldRecordingDataSource().getSurveyor(container!!.context))
        } else {
            // Action edit existing data
            val record = setData(container!!.context, rowId)

            _ffbPalmCode?.setText(record.palm_code)
            _ffbDate?.setText(record.date_ffb_yield)
            _ffbNumOfFreshBunch?.setText(record.num_of_fre_bunch.toString())
            _ffbWeightOfFreshBunch?.setText(record.weight_of_fre_bunch.toString())
            _ffbNumOfRottenBunch?.setText(record.num_of_rot_bunch.toString())
            _ffbWeightOfRottenBunch?.setText(record.weight_of_rot_bunch.toString())
            _ffbRound?.setText(record.round_interval.toString())
            _ffbSupervisor?.setText(record.supervision)
            _ffbSurveyor?.setText(record.record_officer)
        }

        _ffbSaveButton?.setOnClickListener {
            if (doAction == "insert") {
                FfbYieldRecordingDataSource().saveFfbYieldRecording(
                    container!!.context,
                    _ffbDate?.text.toString(),
                    _ffbPalmCode?.text.toString(),
                    shortPalmCode,
                    rowNumber,
                    blockCode,
                    if (_ffbNumOfFreshBunch?.text.toString() == "") 0 else _ffbNumOfFreshBunch?.text.toString().toInt(),
                    if (_ffbWeightOfFreshBunch?.text.toString() == "") 0.0 else roundOffDecimal(_ffbWeightOfFreshBunch?.text.toString().toDouble()),
                    if (_ffbNumOfRottenBunch?.text.toString() == "") 0 else _ffbNumOfRottenBunch?.text.toString().toInt(),
                    if (_ffbWeightOfRottenBunch?.text.toString() == "") 0.0 else roundOffDecimal(_ffbWeightOfRottenBunch?.text.toString().toDouble()),
                    if (_ffbRound?.text.toString() == "") 0 else _ffbRound?.text.toString().toInt(),
                    _ffbSupervisor?.text.toString(),
                    _ffbSurveyor?.text.toString(),
                    getUsername(container.context)
                )
                Toast.makeText(this.context, "Insert new data successfully", Toast.LENGTH_SHORT).show()
            } else {
                FfbYieldRecordingDataSource().updateFfbYieldRecording(
                    container!!.context,
                    rowId,
                    if (_ffbNumOfFreshBunch?.text.toString() == "") 0 else _ffbNumOfFreshBunch?.text.toString().toInt(),
                    if (_ffbWeightOfFreshBunch?.text.toString() == "") 0.0 else roundOffDecimal(_ffbWeightOfFreshBunch?.text.toString().toDouble()),
                    if (_ffbNumOfRottenBunch?.text.toString() == "") 0 else _ffbNumOfRottenBunch?.text.toString().toInt(),
                    if (_ffbWeightOfRottenBunch?.text.toString() == "") 0.0 else roundOffDecimal(_ffbWeightOfRottenBunch?.text.toString().toDouble()),
                    if (_ffbRound?.text.toString() == "") 0 else _ffbRound?.text.toString().toInt(),
                    _ffbSupervisor?.text.toString(),
                    _ffbSurveyor?.text.toString(),
                )
                Toast.makeText(this.context, "Update data successfully", Toast.LENGTH_SHORT).show()
            }
            FfbYieldRecordingDataSource().setSupervisorSurveyor(container.context, _ffbSupervisor?.text.toString(), _ffbSurveyor?.text.toString())
            findNavController().navigate(R.id.HomeFragment)
        }

        if (_ffbSupervisor?.text.toString() == "" && _ffbSurveyor?.text.toString() == "") {
            _ffbSaveButton?.isEnabled = false
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

    private fun setData(context: Context, rowId: Int): FfbYieldEntity {
        var record: FfbYieldEntity
        runBlocking {
            val recording = FfbYieldRecordingDataSource().getRecordingById(context, rowId)
            record = recording
        }
        return record
    }
}
