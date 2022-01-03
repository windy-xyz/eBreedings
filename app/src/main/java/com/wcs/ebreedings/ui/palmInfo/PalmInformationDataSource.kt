package com.wcs.ebreedings.ui.palmInfo

import android.content.Context
import com.wcs.ebreedings.data.entity.PalmEntity
import com.wcs.ebreedings.data.storage.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PalmInformationDataSource {

    suspend fun getPalms(context: Context): List<PalmEntity> = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.PalmDao().getPalms()
    }

    suspend fun getPalmByQrCodeNo(context: Context, qrCodeNo: String): PalmEntity = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.PalmDao().getPalmByQrCodeNo(qrCodeNo)
    }

}
