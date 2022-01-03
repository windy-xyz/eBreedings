package com.wcs.ebreedings.ui.login

import android.content.Context
import com.wcs.ebreedings.api.ApiInterface
import com.wcs.ebreedings.api.CallbackInterface
import com.wcs.ebreedings.api.RestApi
import com.wcs.ebreedings.data.entity.PalmEntity
import com.wcs.ebreedings.data.model.LoginResponse
import com.wcs.ebreedings.data.storage.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import kotlin.experimental.and

class LoginDataSource {

    fun login(username: String, password: String, estate: String, updateMaster: Int, context: Context, callback: CallbackInterface<LoginResponse>) {

        val retrofitInstance = RestApi.getRetrofitInstance().create(ApiInterface::class.java)
        retrofitInstance.login(username, getMd5Key(password), estate, updateMaster).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                CoroutineScope(Dispatchers.IO).launch {
                    if (updateMaster != response.body()?.currentMaster) {
                        val db = AppDatabase.getAppDataBase(context)
                        val palms = response.body()?.masterDataPalm
                        if (palms != null) {
                            for (palm in palms) {
                                val palmEntity = PalmEntity(
                                    palm_code = palm.palm_code,
                                    palm_number = palm.palm_number,
                                    palm_row_number = palm.palm_row_number,
                                    progeny_id = palm.progeny_id,
                                    progeny_desc = palm.progeny_desc,
                                    parental_male = palm.parental_male,
                                    parental_male_desc = palm.parental_male_desc,
                                    parental_female = palm.parental_female,
                                    parental_female_desc = palm.parental_female_desc,
                                    fruit_type = palm.fruit_type,
                                    estate_code = palm.estate_code,
                                    estate_name = palm.estate_name,
                                    division_code = palm.division_code,
                                    division_name = palm.division_name,
                                    block_no = palm.block_no,
                                    trial_area = palm.trial_area,
                                    year_of_planting = palm.year_of_planting,
                                    mature_stage = palm.mature_stage,
                                    palm_status = palm.palm_status,
                                    palm_status2 = palm.palm_status2,
                                    qr_code_no = palm.qr_code_no,
                                    max_bunches = palm.max_bunches
                                )
                                db?.PalmDao()?.insertPalm(palmEntity)
                            }
                        }
                    }
                    callback.onDataResponse(response.body())
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onDataFailure(t.message)
            }
        })

    }

    private fun getMd5Key(password: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(password.toByteArray())
            val byteData = md.digest()

            //convert the byte to hex format method 1
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(Integer.toString((byteData[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }

            //convert the byte to hex format method 2
            val hexString = StringBuffer()
            for (i in byteData.indices) {
                val hex = Integer.toHexString(0xff and byteData[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            return hexString.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
