package com.wcs.ebreedings.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "palm")
data class PalmEntity(
    @PrimaryKey(autoGenerate = true)
    val palm_id: Int = 0,
    val palm_code: String,
    val palm_number: String,
    val palm_row_number: String,
    val progeny_id: String,
    val progeny_desc: String,
    val parental_male: String,
    val parental_male_desc: String,
    val parental_female: String,
    val parental_female_desc: String,
    val fruit_type: String,
    val estate_code: String,
    val estate_name: String,
    val division_code: String,
    val division_name: String,
    val block_no: String,
    val trial_area: String,
    val year_of_planting: String,
    val mature_stage: String,
    val palm_status: String,
    val palm_status2: String,
    val qr_code_no: String,
    val max_bunches: String
)
