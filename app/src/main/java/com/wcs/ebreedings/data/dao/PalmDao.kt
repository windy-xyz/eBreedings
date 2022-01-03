package com.wcs.ebreedings.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wcs.ebreedings.data.entity.PalmEntity

@Dao
interface PalmDao{
    @Insert
    fun insertPalm(palmEntity: PalmEntity)

    @Query("select * from palm")
    fun getPalms(): List<PalmEntity>

    @Query("select * from palm where qr_code_no like :qrCodeNo")
    fun getPalmByQrCodeNo(qrCodeNo: String): PalmEntity

}
