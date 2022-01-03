package com.wcs.ebreedings.ui.vegMeasurment

import android.content.Context
import com.wcs.ebreedings.data.entity.VegMeasEntity
import com.wcs.ebreedings.data.storage.AppDatabase
import com.wcs.ebreedings.data.storage.Pref
import com.wcs.ebreedings.others.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VegMeasurementDataSource {

    fun saveVegMeasurement(
        context: Context,
        dateVegMeas: String,
        palmCode: String,
        shortPalmCode: String,
        rowNumber: String,
        blockCode: String,
        leafSamNo: Int,
        palmHeight: Double,
        petioleWidth: Double,
        petioleThickness: Double,
        petCrossSec: Double,
        leafletsSam1Width: Double,
        leafletsSam1Length: Double,
        leafletsSam2Width: Double,
        leafletsSam2Length: Double,
        leafletsSam3Width: Double,
        leafletsSam3Length: Double,
        leafletsSam4Width: Double,
        leafletsSam4Length: Double,
        leafletsSam5Width: Double,
        leafletsSam5Length: Double,
        leafletsSam6Width: Double,
        leafletsSam6Length: Double,
        numOfLeaflets: Double,
        palmsFrondLength: Double,
        palmsFrondProd: Double,
        leafArea: Double,
        palmTrunkCircum: Double,
        supervision: String,
        recordOfficer: String,
        createBy: String
    ) {
        val formattedDateTime = DateConverter.getFormattedDateTime()

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getAppDataBase(context)
            val vegMeasEntity = VegMeasEntity(
                palm_code = palmCode,
                short_palm_code = shortPalmCode,
                row_number = rowNumber,
                date_veg_meas = dateVegMeas,
                block_code = blockCode,
                leaf_sam_no = leafSamNo,
                palm_height = palmHeight,
                petiole_width = petioleWidth,
                petiole_thickness = petioleThickness,
                pet_cross_sec = petCrossSec,
                leaflets_sam1_width = leafletsSam1Width,
                leaflets_sam1_length = leafletsSam1Length,
                leaflets_sam2_width = leafletsSam2Width,
                leaflets_sam2_length = leafletsSam2Length,
                leaflets_sam3_width = leafletsSam3Width,
                leaflets_sam3_length = leafletsSam3Length,
                leaflets_sam4_width = leafletsSam4Width,
                leaflets_sam4_length = leafletsSam4Length,
                leaflets_sam5_width = leafletsSam5Width,
                leaflets_sam5_length = leafletsSam5Length,
                leaflets_sam6_width = leafletsSam6Width,
                leaflets_sam6_length = leafletsSam6Length,
                num_of_leaflets = numOfLeaflets,
                palms_frond_length = palmsFrondLength,
                palms_frond_prod = palmsFrondProd,
                leaf_area = leafArea,
                palm_trunk_circum = palmTrunkCircum,
                supervision = supervision,
                record_officer = recordOfficer,
                create_date = formattedDateTime,
                create_by = createBy
            )

            db?.VegMeasDao()?.insertNewMeasurement(vegMeasEntity)
        }
    }

    fun updateVegMeasurement(
        context: Context,
        rowId: Int,
        leafSamNo: Int,
        palmHeight: Double,
        petioleWidth: Double,
        petioleThickness: Double,
        petCrossSec: Double,
        leafletsSam1Width: Double,
        leafletsSam1Length: Double,
        leafletsSam2Width: Double,
        leafletsSam2Length: Double,
        leafletsSam3Width: Double,
        leafletsSam3Length: Double,
        leafletsSam4Width: Double,
        leafletsSam4Length: Double,
        leafletsSam5Width: Double,
        leafletsSam5Length: Double,
        leafletsSam6Width: Double,
        leafletsSam6Length: Double,
        numOfLeaflets: Double,
        palmsFrondLength: Double,
        palmsFrondProd: Double,
        leafArea: Double,
        palmTrunkCircum: Double,
        supervision: String,
        recordOfficer: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getAppDataBase(context)
           db?.VegMeasDao()?.updateMeasurement(
               rowId,
               leafSamNo,
               palmHeight,
               petioleWidth,
               petioleThickness,
               petCrossSec,
               leafletsSam1Width,
               leafletsSam1Length,
               leafletsSam2Width,
               leafletsSam2Length,
               leafletsSam3Width,
               leafletsSam3Length,
               leafletsSam4Width,
               leafletsSam4Length,
               leafletsSam5Width,
               leafletsSam5Length,
               leafletsSam6Width,
               leafletsSam6Length,
               numOfLeaflets,
               palmsFrondLength,
               palmsFrondProd,
               leafArea,
               palmTrunkCircum,
               supervision,
               recordOfficer
           )
        }
    }

    suspend fun getMeasurementById(context: Context, rowId: Int): VegMeasEntity = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        return@withContext db!!.VegMeasDao().getMeasurementById(rowId)
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