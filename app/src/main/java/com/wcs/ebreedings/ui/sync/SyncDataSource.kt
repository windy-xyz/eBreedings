package com.wcs.ebreedings.ui.sync

import android.content.Context
import com.wcs.ebreedings.api.ApiInterface
import com.wcs.ebreedings.api.CallbackInterface
import com.wcs.ebreedings.api.RestApi
import com.wcs.ebreedings.data.entity.FfbYieldEntity
import com.wcs.ebreedings.data.entity.VegMeasEntity
import com.wcs.ebreedings.data.storage.AppDatabase
import com.wcs.ebreedings.others.DateConverter
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class SyncDataSource {

    fun syncData(context: Context, callback: CallbackInterface<SyncResponse>) {
        CoroutineScope(Dispatchers.IO).launch {
            val jsonBody = createJsonBody(context)

            try {
                val retrofitInstance = RestApi.getRetrofitInstance().create(ApiInterface::class.java)
                retrofitInstance.insertData(jsonBody.toString()).enqueue(object : Callback<SyncResponse> {
                    override fun onResponse(call: Call<SyncResponse>, response: Response<SyncResponse>) {
                        runBlocking {
                            removeHistory(context)
                            createBackUpFile(context, jsonBody.toString())
                        }
                        callback.onDataResponse(response.body())
                    }

                    override fun onFailure(call: Call<SyncResponse>, t: Throwable) {
                        t.printStackTrace()
                        callback.onDataFailure(t.message)
                    }

                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun createJsonBody(context: Context): JSONObject {
        val jsonBody = JSONObject()
        val jsonArrayFfb = JSONArray()
        val jsonArrayVeg = JSONArray()

        val db = AppDatabase.getAppDataBase(context)

        val ffbRecords: List<FfbYieldEntity> = db!!.FfbYieldDao().getAllRecordings()
        if (ffbRecords.isNotEmpty()) {
            for (record in ffbRecords) {
                val jsonObjectFfb = JSONObject()
                jsonObjectFfb.put("date_ffb_yield", record.date_ffb_yield)
                jsonObjectFfb.put("palm_code", record.palm_code.trim())
                jsonObjectFfb.put("block_code", record.block_code)
                jsonObjectFfb.put("num_of_fre_bunch", record.num_of_fre_bunch)
                jsonObjectFfb.put("weight_of_fre_bunch", record.weight_of_fre_bunch)
                jsonObjectFfb.put("num_of_rot_bunch", record.num_of_rot_bunch)
                jsonObjectFfb.put("weight_of_rot_bunch", record.weight_of_rot_bunch)
                jsonObjectFfb.put("round_interval", record.round_interval)
                jsonObjectFfb.put("supervision", record.supervision)
                jsonObjectFfb.put("record_officer", record.record_officer)
                jsonObjectFfb.put("create_date", record.create_date)
                jsonObjectFfb.put("create_by", record.create_by)

                jsonArrayFfb.put(jsonObjectFfb)
            }
        }

        val vegMeasurements: List<VegMeasEntity> = db.VegMeasDao().getAllMeasurements()
        if (vegMeasurements.isNotEmpty()) {
            for (measurement in vegMeasurements) {
                val jsonObjectVeg = JSONObject()
                jsonObjectVeg.put("date_veg_meas", measurement.date_veg_meas)
                jsonObjectVeg.put("palm_code", measurement.palm_code)
                jsonObjectVeg.put("block_code", measurement.block_code)
                jsonObjectVeg.put("leaf_sam_no", measurement.leaf_sam_no)
                jsonObjectVeg.put("palm_height", measurement.palm_height)
                jsonObjectVeg.put("petiole_width", measurement.petiole_width)
                jsonObjectVeg.put("petiole_thickness", measurement.petiole_thickness)
                jsonObjectVeg.put("pet_cross_sec", measurement.pet_cross_sec)
                jsonObjectVeg.put("leaflets_sam1_width", measurement.leaflets_sam1_width)
                jsonObjectVeg.put("leaflets_sam1_length", measurement.leaflets_sam1_length)
                jsonObjectVeg.put("leaflets_sam2_width", measurement.leaflets_sam2_width)
                jsonObjectVeg.put("leaflets_sam2_length", measurement.leaflets_sam2_length)
                jsonObjectVeg.put("leaflets_sam3_width", measurement.leaflets_sam3_width)
                jsonObjectVeg.put("leaflets_sam3_length", measurement.leaflets_sam3_length)
                jsonObjectVeg.put("leaflets_sam4_width", measurement.leaflets_sam4_width)
                jsonObjectVeg.put("leaflets_sam4_length", measurement.leaflets_sam4_length)
                jsonObjectVeg.put("leaflets_sam5_width", measurement.leaflets_sam5_width)
                jsonObjectVeg.put("leaflets_sam5_length", measurement.leaflets_sam5_length)
                jsonObjectVeg.put("leaflets_sam6_width", measurement.leaflets_sam6_width)
                jsonObjectVeg.put("leaflets_sam6_length", measurement.leaflets_sam6_length)
                jsonObjectVeg.put("num_of_leaflets", measurement.num_of_leaflets)
                jsonObjectVeg.put("palms_frond_length", measurement.palms_frond_length)
                jsonObjectVeg.put("palms_frond_prod", measurement.palms_frond_prod)
                jsonObjectVeg.put("leaf_area", measurement.leaf_area)
                jsonObjectVeg.put("palm_trunk_circum", measurement.palm_trunk_circum)
                jsonObjectVeg.put("supervision", measurement.supervision)
                jsonObjectVeg.put("record officer", measurement.record_officer)
                jsonObjectVeg.put("create_date", measurement.create_date)
                jsonObjectVeg.put("create_by", measurement.create_by)

                jsonArrayVeg.put(jsonObjectVeg)
            }
        }

        jsonBody.put("T_FFB_Yield_det", jsonArrayFfb)
        jsonBody.put("T_veg_meas_det", jsonArrayVeg)

        return jsonBody
    }

    suspend fun removeHistory(context: Context) = withContext(Dispatchers.IO) {
        val db = AppDatabase.getAppDataBase(context)
        if (db != null) {
            db.FfbYieldDao().deleteAll()
            db.VegMeasDao().deleteAll()
        }
    }

    private fun createBackUpFile(context: Context, jsonBody: String) {
        val fDate:String = DateConverter.getFormattedDateTime2()
        val fName = "eBreedings-"
        val fExtension = ".txt"
        val fileName: String = fName + fDate + fExtension

        val file = File(context.getExternalFilesDir(null)!!.absolutePath, fileName)
        file.writeText(jsonBody)
    }
}
