package com.wcs.ebreedings.ui.history

import android.content.Context
import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.entity.VegMeasEntity
import com.wcs.ebreedings.data.storage.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryDataSource {

    suspend fun getAllRecordings(context: Context): List<FfbYieldEntity> = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.FfbYieldDao().getAllRecordings()
    }

    suspend fun getAllMeasurements(context: Context): List<VegMeasEntity> = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.VegMeasDao().getAllMeasurements()
    }

}
