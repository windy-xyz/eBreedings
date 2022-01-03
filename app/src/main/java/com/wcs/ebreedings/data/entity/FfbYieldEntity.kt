package com.wcs.ebreedings.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ffb_yield_recording")
data class FfbYieldEntity(
    @PrimaryKey(autoGenerate = true)
    var recording_id: Int = 0,
    var palm_code: String,
    var short_palm_code: String,
    var row_number: String,
    var date_ffb_yield: String,
    var block_code: String,
    var num_of_fre_bunch: Int,
    var weight_of_fre_bunch: Double,
    var num_of_rot_bunch: Int,
    var weight_of_rot_bunch: Double,
    var round_interval: Int,
    var supervision: String,
    var record_officer: String,
    var create_date: String,
    var create_by: String
)
