package com.wcs.ebreedings.ui.ffbYieldRecording

import android.content.Context
import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.storage.AppDatabase
import com.wcs.ebreedings.data.storage.Pref
import com.wcs.ebreedings.others.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FfbYieldRecordingDataSource {

    fun saveFfbYieldRecording(
        context: Context,
        dateFfbYield: String,
        palmCode: String,
        shortPalmCode: String,
        rowNumber: String,
        blockCode: String,
        numOfFreBunch: Int,
        weightOfFreBunch: Double,
        numOfRotBunch: Int,
        weightOfRotBunch: Double,
        roundInterval: Int,
        supervision: String,
        recordOfficer: String,
        createBy: String
    ) {
        val formattedDateTime = DateConverter.getFormattedDateTime()

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getAppDataBase(context)
            val ffbYieldEntity = FfbYieldEntity(
                palm_code = palmCode,
                short_palm_code = shortPalmCode,
                row_number = rowNumber,
                date_ffb_yield = dateFfbYield,
                block_code = blockCode,
                num_of_fre_bunch = numOfFreBunch,
                weight_of_fre_bunch = weightOfFreBunch,
                num_of_rot_bunch = numOfRotBunch,
                weight_of_rot_bunch = weightOfRotBunch,
                round_interval = roundInterval,
                supervision = supervision,
                record_officer = recordOfficer,
                create_date = formattedDateTime,
                create_by = createBy
            )

            db?.FfbYieldDao()?.insertNewRecording(ffbYieldEntity)
        }
    }

    fun updateFfbYieldRecording(
        context: Context,
        rowId: Int,
        numOfFreBunch: Int,
        weightOfFreBunch: Double,
        numOfRotBunch: Int,
        weightOfRotBunch: Double,
        roundInterval: Int,
        supervision: String,
        recordOfficer: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getAppDataBase(context)
            db?.FfbYieldDao()?.updateRecording(
                rowId,
                numOfFreBunch,
                weightOfFreBunch,
                numOfRotBunch,
                weightOfRotBunch,
                roundInterval,
                supervision,
                recordOfficer
            )
        }
    }

    suspend fun getRecordingById(context: Context, rowId: Int): FfbYieldEntity = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.FfbYieldDao().getRecordingById(rowId)
    }

    fun setSupervisorSurveyor(context: Context, supervisor: String, surveyor: String) {
        val pref = Pref(context)
        pref.saveSupervisor(supervisor)
        pref.saveSurveyor(surveyor)
    }

    fun getSupervisor(context: Context): String {
        val pref = Pref(context)
        return pref.getSupervisor()
    }

    fun getSurveyor(context: Context): String {
        val pref = Pref(context)
        return pref.getSurveyor()
    }
}
