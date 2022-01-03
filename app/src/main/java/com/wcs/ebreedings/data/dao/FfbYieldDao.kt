package com.wcs.ebreedings.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.wcs.ebreedings.data.entity.FfbYieldEntity

@Dao
interface FfbYieldDao {
    @Query("select * from ffb_yield_recording")
    fun getAllRecordings(): List<FfbYieldEntity>

    @Query("select * from ffb_yield_recording where recording_id = :id")
    fun getRecordingById(id: Int): FfbYieldEntity

    @Insert
    fun insertNewRecording(ffbYieldEntity: FfbYieldEntity)

    @Query("delete from ffb_yield_recording")
    fun deleteAll()

    @Query("update ffb_yield_recording set " +
            "num_of_fre_bunch = :num_of_fre_bunch, " +
            "weight_of_fre_bunch = :weight_of_fre_bunch, " +
            "num_of_rot_bunch = :num_of_rot_bunch, " +
            "weight_of_rot_bunch = :weight_of_rot_bunch, " +
            "round_interval = :round_interval, " +
            "supervision = :supervision, " +
            "record_officer = :record_officer " +
            "where recording_id = :id")
    fun updateRecording(
        id: Int,
        num_of_fre_bunch: Int,
        weight_of_fre_bunch: Double,
        num_of_rot_bunch: Int,
        weight_of_rot_bunch: Double,
        round_interval: Int,
        supervision: String,
        record_officer: String
    )

}
